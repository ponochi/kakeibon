package org.panda.systems.kakeibon.app.spec;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.panda.systems.kakeibon.app.account.AccountDestinationForm;
import org.panda.systems.kakeibon.app.account.AccountSourceForm;
import org.panda.systems.kakeibon.app.common.*;
import org.panda.systems.kakeibon.app.currency.CurrencyListForm;
import org.panda.systems.kakeibon.app.shop.ShopForm;
import org.panda.systems.kakeibon.app.users.UsersExtForm;
import org.panda.systems.kakeibon.domain.model.account.AccountDestination;
import org.panda.systems.kakeibon.domain.model.account.AccountSource;
import org.panda.systems.kakeibon.domain.model.common.AccountAndBalance;
import org.panda.systems.kakeibon.domain.model.common.BalanceType;
import org.panda.systems.kakeibon.domain.model.common.Shop;
import org.panda.systems.kakeibon.domain.model.spec.Specification;
import org.panda.systems.kakeibon.domain.model.spec.SpecificationGroup;
import org.panda.systems.kakeibon.domain.model.users.Users;
import org.panda.systems.kakeibon.domain.model.users.UsersExt;
import org.panda.systems.kakeibon.domain.service.account.AccountDestinationService;
import org.panda.systems.kakeibon.domain.service.account.AccountSourceService;
import org.panda.systems.kakeibon.domain.service.common.*;
import org.panda.systems.kakeibon.domain.service.currency.CurrencyListService;
import org.panda.systems.kakeibon.domain.service.spec.SpecificationGroupService;
import org.panda.systems.kakeibon.domain.service.spec.SpecificationService;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetails;
import org.panda.systems.kakeibon.domain.service.users.CustomUserDetailsService;
import org.panda.systems.kakeibon.domain.service.users.UserExtsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
//@RequestMapping("")
public class SpecificationController extends HttpServlet implements Serializable {

    @Autowired
    private SpecificationGroupService specificationGroupService;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private UserExtsService userExtsService;
    @Autowired
    private ShopService shopService;
    @Autowired
    private BalanceTypeService balanceTypeService;
    @Autowired
    private AccountAndBalanceService accountAndBalanceService;
    @Autowired
    private AccountSourceService accountSourceService;
    @Autowired
    private AccountDestinationService accountDestinationService;
    @Autowired
    private CurrencyListService currencyListService;
    @Autowired
    private UnitService unitService;
    @Autowired
    private TaxTypeService taxTypeService;
    @Autowired
    private TaxRateService taxRateService;
    @Autowired
    private CustomUserDetails customUserDetails;

    private Long ui;

    private HttpSession session;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // Default constructor
    public SpecificationController() {

    }

    // Constructor Injection
    public SpecificationController(
            SpecificationGroupService specificationGroupService,
            SpecificationService specificationService,
            CustomUserDetailsService customUserDetailsService,
            UserExtsService userExtsService,
            ShopService shopService,
            BalanceTypeService balanceTypeService,
            AccountAndBalanceService accountAndBalanceService,
            AccountSourceService accountSourceService,
            AccountDestinationService accountDestinationService,
            CurrencyListService currencyListService,
            UnitService unitService,
            TaxTypeService taxTypeService,
            TaxRateService taxRateService,
            CustomUserDetails customUserDetails) {
        this.specificationGroupService = specificationGroupService;
        this.specificationService = specificationService;
        this.customUserDetailsService = customUserDetailsService;
        this.userExtsService = userExtsService;
        this.shopService = shopService;
        this.balanceTypeService = balanceTypeService;
        this.accountAndBalanceService = accountAndBalanceService;
        this.accountSourceService = accountSourceService;
        this.accountDestinationService = accountDestinationService;
        this.currencyListService = currencyListService;
        this.unitService = unitService;
        this.taxTypeService = taxTypeService;
        this.taxRateService = taxRateService;
        this.customUserDetails = customUserDetails;
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        try {
            response.setContentType("text/html; charset=UTF-8"); //処理１
            //セッションオブジェクトの生成
            session = request.getSession();
            //セッションへのデータの登録
            ui = (Long) session.getAttribute("ui");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @RequestMapping(path = "/spec", method = {RequestMethod.GET, RequestMethod.POST})
    String index(
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @ModelAttribute UsersExtForm usersExtForm,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        List<SpecificationGroup> groups
                = specificationGroupService.findAllByUsernameAndDeleted(
                userDetails.getUsername(),
                false);

        Optional<Long> userId = Optional.<Long>empty();
        userId = Optional.of(
                customUserDetailsService.findByUsername(
                        userDetails.getUsername()).getUsers().getUserId());

        assert groups != null;

        List<SpecificationGroupForm> groupForms = new ArrayList<>();

        for (SpecificationGroup group : groups) {
            SpecificationGroupForm groupForm
                    = new SpecificationGroupForm(
                    specificationGroupService,
                    specificationService,
                    accountAndBalanceService,
                    accountSourceService,
                    accountDestinationService,
                    customUserDetailsService,
                    userExtsService,
                    shopService,
                    balanceTypeService,
                    currencyListService,
                    unitService,
                    taxTypeService,
                    taxRateService);

            groupForm = groupForm.setSpecificationGroupToForm(group);

            UsersExt usersExt
                    = userExtsService.findByUserId(group.getUserId());

            if (usersExt != null && usersExt.getUserId() != null) {
                userId = usersExt.getUserId().describeConstable();
                if (userId.isPresent() && ui == null) {
                    ui = userId.get();
                    if (session == null) {
                        session = request.getSession();
                    }
                    session.setAttribute("ui", ui);
                }

                usersExtForm = usersExtForm.setUserExtToForm(usersExt);
                groupForm.setUsersExtForm(usersExtForm);
            }


            AccountAndBalance accountAndBalance
                    = accountAndBalanceService.findByAccountAndBalanceId(
                    groupForm.getAccountAndBalanceId());

            AccountAndBalanceForm accountAndBalanceForm
                    = new AccountAndBalanceForm()
                    .setAccountAndBalanceToForm(accountAndBalance);

            groupForm.setAccountAndBalanceId(
                    accountAndBalanceForm.getAccountAndBalanceId());
            groupForm.setAccountAndBalanceForm(
                    accountAndBalanceForm);

            AccountSourceForm accountSourceForm = new AccountSourceForm(
                    accountSourceService)
                    .setAccountSourceFormByDB(
                            groupForm
                                    .getAccountAndBalanceForm()
                                    .getAccountSourceId());
            accountAndBalanceForm
                    .setAccountSourceId(
                            accountSourceForm.getAccountSourceId());
            accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

            AccountDestinationForm accountDestinationForm
                    = new AccountDestinationForm(
                    accountDestinationService)
                    .setAccountDestinationFormByDB(
                            groupForm.getAccountAndBalanceForm()
                                    .getAccountDestinationId());
            accountAndBalanceForm.setAccountDestinationId(
                    accountDestinationForm.getAccountDestinationId());
            accountAndBalanceForm.setAccountDestinationForm(
                    accountDestinationForm);

            Shop shop = shopService.findById(
                    groupForm.getShopId());
            groupForm.setShopId(shop.getShopId());
            groupForm.setShopToForm(shop);

            BalanceType balanceType
                    = balanceTypeService.findByBalanceTypeId(
                    groupForm.getBalanceTypeId());
            groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
            groupForm.setBalanceTypeToForm(balanceType);
            groupForms.add(groupForm);
        }

        model.addAttribute("specificationGroupForms", groupForms);
        model.addAttribute("userDetails", userDetails);
        assert Objects.requireNonNull(userId).isPresent();
        model.addAttribute("userId", userId.orElse(ui));
        model.addAttribute("ui", ui);
        return "spec/showList";
    }

    @GetMapping(value = "/spec/create/group/{userId}")
    String cancelCreateSpecificationGroup(
            @PathVariable("userId") Long userId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            Model model) {

        Users users = customUserDetailsService.findByUserId(userId);

        groupForm = new SpecificationGroupForm(
                specificationGroupService,
                specificationService,
                accountAndBalanceService,
                accountSourceService,
                accountDestinationService,
                customUserDetailsService,
                userExtsService,
                shopService,
                balanceTypeService,
                currencyListService,
                unitService,
                taxTypeService,
                taxRateService);

        groupForm.setUserId(userId);
        groupForm.setUsername(userDetails.getUsername());
        groupForm.setReceivingAndPaymentDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()));
        groupForm.setReceivingAndPaymentTime(LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), 0));
        groupForm.setAccountAndBalanceId(accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setDeleted(false);
        groupForm.setUsersToForm(
                customUserDetailsService,
                users);

        Shop shop = shopService.findById(Long.parseLong("1"));
        groupForm.setShopId(shop.getShopId());
        groupForm.setShopToForm(shop);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(Long.parseLong("1"));
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        Long.parseLong("1"));
        accountAndBalanceForm.setAccountSourceId(
                accountSourceForm.getAccountSourceId());
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        accountDestinationForm = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        Long.parseLong("1"));
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationForm.getAccountDestinationId());
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);

        AccountAndBalance accountAndBalance
                = accountAndBalanceService.saveAndFlush(
                accountAndBalanceForm.toEntity());

        accountAndBalanceForm
                .setAccountAndBalanceToForm(accountAndBalance);

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        specificationGroupService
                .saveAndFlush(groupForm.toEntity());

        SpecificationGroup specificationGroup
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupService.getMaxGroupId(),
                        userId,
                        false);
        groupForm = groupForm.setSpecificationGroupToForm(specificationGroup);

        model.addAttribute("specificationGroupForm", groupForm);
        model.addAttribute("accountAndBalanceId",
                groupForm.getAccountAndBalanceForm().getAccountAndBalanceId());
        model.addAttribute("shopForm", groupForm.getShopForm());
        model.addAttribute("accountSourceForm",
                new AccountSourceForm(
                        accountSourceService)
                        .setAccountSourceFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountSourceId()));
        model.addAttribute("accountDestinationForm",
                new AccountDestinationForm(
                        accountDestinationService)
                        .setAccountDestinationFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountDestinationId()));
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("balanceTypes", balanceTypeService.findAll());
        model.addAttribute("selectedBalanceTypeId", groupForm.getBalanceTypeId());

        return "spec/createGroup";
    }

    @PostMapping(value = "/spec/create/group/{ui}")
    String createSpecificationGroup(
            @PathVariable("ui") Long ui,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model,
            HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        Users users = customUserDetailsService.findByUserId(ui);

        groupForm = new SpecificationGroupForm(
                specificationGroupService,
                specificationService,
                accountAndBalanceService,
                accountSourceService,
                accountDestinationService,
                customUserDetailsService,
                userExtsService,
                shopService,
                balanceTypeService,
                currencyListService,
                unitService,
                taxTypeService,
                taxRateService);

        SpecificationGroup group
                = new SpecificationGroup();

        groupForm.setUserId(ui);
        groupForm.setUsername(userDetails.getUsername());
        groupForm.setReceivingAndPaymentDate(LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth()));
        groupForm.setReceivingAndPaymentTime(LocalTime.of(LocalTime.now().getHour(), LocalTime.now().getMinute(), 0));
        groupForm.setAccountAndBalanceId(accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setDeleted(false);
        groupForm.setUsersToForm(
                customUserDetailsService,
                users);

        Shop shop = shopService.findById(Long.parseLong("1"));
        groupForm.setShopId(shop.getShopId());
        groupForm.setShopToForm(shop);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(Long.parseLong("1"));
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        Long.parseLong("1"));
        accountAndBalanceForm.setAccountSourceId(
                accountSourceForm.getAccountSourceId());
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        accountDestinationForm = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        Long.parseLong("1"));
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationForm.getAccountDestinationId());
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);

        AccountAndBalance accountAndBalance
                = accountAndBalanceService.saveAndFlush(
                accountAndBalanceForm.toEntity());

        accountAndBalanceForm
                .setAccountAndBalanceToForm(accountAndBalance);

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        specificationGroupService
                .saveAndFlush(groupForm.toEntity());

        SpecificationGroup specificationGroup
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupService.getMaxGroupId(),
                        ui,
                        false);
        groupForm = groupForm.setSpecificationGroupToForm(specificationGroup);

        model.addAttribute("specificationGroupForm", groupForm);
        model.addAttribute(
                "accountAndBalanceId",
                groupForm.getAccountAndBalanceForm().getAccountAndBalanceId());
        model.addAttribute("shopForm", groupForm.getShopForm());
        model.addAttribute(
                "accountSourceForm",
                new AccountSourceForm(
                        accountSourceService)
                        .setAccountSourceFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountSourceId()));
        model.addAttribute(
                "accountDestinationForm",
                new AccountDestinationForm(
                        accountDestinationService)
                        .setAccountDestinationFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountDestinationId()));
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("balanceTypes", balanceTypeService.findAll());
        model.addAttribute("selectedBalanceTypeId", groupForm.getBalanceTypeId());

        return "spec/createGroup";
    }

    @GetMapping(value = "/spec/edit/group/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/{receivingAndPaymentDate}/{receivingAndPaymentTime}")
    String cancelEditSpecificationGroup(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @PathVariable("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @RequestParam(name = "groupMemo", required = false) String groupMemo,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        groupForm
                = groupForm.setSpecificationGroupToForm(group);

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(
                accountAndBalanceForm);

        accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountAndBalanceForm.setAccountSourceId(accountSourceId);
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        accountDestinationForm = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationId);
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }


        model.addAttribute("specificationGroupForm", groupForm);
        model.addAttribute("specificationForms", detailForms);
        model.addAttribute("currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute("taxTypeForm", taxTypeForm);
        model.addAttribute("taxRateForm", taxRateForm);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "accountAndBalanceForm",
                accountAndBalanceForm);
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute(
                "balanceTypes", balanceTypeService.findAll());
        model.addAttribute(
                "selectedBalanceTypeId", groupForm.getBalanceTypeId());
        model.addAttribute(
                "totalAmount", Math.round(totalAmount));

        model.addAttribute(
                "groupMemo", groupForm.getGroupMemo());

        return "spec/editGroup";
    }

    @PostMapping(value = "/spec/edit/group/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}")
    String editSpecificationGroup(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        groupForm
                = groupForm.setSpecificationGroupToForm(group);

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(
                accountAndBalanceForm);

        accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountAndBalanceForm.setAccountSourceId(accountSourceId);
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        accountDestinationForm = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationId);
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }


        model.addAttribute("specificationGroupForm", groupForm);
        model.addAttribute("specificationForms", detailForms);
        model.addAttribute("currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute("taxTypeForm", taxTypeForm);
        model.addAttribute("taxRateForm", taxRateForm);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "accountAndBalanceForm",
                accountAndBalanceForm);
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute(
                "balanceTypes", balanceTypeService.findAll());
        model.addAttribute(
                "selectedBalanceTypeId", groupForm.getBalanceTypeId());
        model.addAttribute(
                "totalAmount", Math.round(totalAmount));

        model.addAttribute(
                "groupMemo", groupForm.getGroupMemo());

        return "spec/editGroup";
    }

    @GetMapping(value = "/spec/create/detail/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/{receivingAndPaymentDate}/{receivingAndPaymentTime}")
    String cancelCreateSpecification(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @PathVariable("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @RequestParam(name = "groupMemo", required = false) String groupMemo,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        groupForm.setReceivingAndPaymentDate(receivingAndPaymentDate);
        groupForm.setReceivingAndPaymentTime(receivingAndPaymentTime);

        shopForm.setShopId(shopId);
        groupForm.setShopForm(shopForm);

        accountSourceForm.setAccountSourceId(accountSourceId);
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);
        accountDestinationForm.setAccountDestinationId(accountDestinationId);
        accountAndBalanceForm.setAccountDestinationForm(accountDestinationForm);
        groupForm.setAccountAndBalanceForm(accountAndBalanceForm);

        groupForm.setGroupMemo(groupMemo);
        groupForm.setDeleted(true);
        specificationGroupService.saveAndFlush(groupForm.toEntity());
        SpecificationGroup group
                = specificationGroupService.findBySpecificationGroupIdAndUserIdAndDeleted(
                groupForm.getSpecificationGroupId(), groupForm.getUserId(), true);

        detailForm
                = new SpecificationForm(
                specificationService,
                specificationGroupId);
        detailForm.setDeleted(false);

        List<SpecificationForm> specificationForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        if (specificationForms.size() == 1) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specificationForms.getFirst().getCurrencyId());
            specificationForms.getFirst().setCurrencyId(
                    currencyListForm.getCurrencyId());
            specificationForms.getFirst().setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specificationForms.getFirst().getUnitId());
            specificationForms.getFirst().setUnitId(unitForm.getUnitId());
            specificationForms.getFirst().setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specificationForms.getFirst().getTaxTypeId());
            specificationForms.getFirst().setTaxTypeId(taxTypeForm.getTaxTypeId());
            specificationForms.getFirst().setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specificationForms.getFirst().getTaxRateId());
            specificationForms.getFirst().setTaxRateId(taxRateForm.getTaxRateId());
            specificationForms.getFirst().setTaxRateForm(taxRateForm);

            specificationForms.getFirst().setDeleted(true);

            specificationService.saveAndFlush(
                    specificationForms.getFirst().toEntity());
        }

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForm", detailForm);
        model.addAttribute(
                "accountAndBalanceForm",
                accountAndBalanceForm);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("groupMemo", groupMemo);

        return "spec/showList";
    }

    @PostMapping(value = "/spec/create/detail/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}")
    String createSpecification(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @RequestParam(name = "groupMemo") String groupMemo,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        groupForm.setReceivingAndPaymentDate(receivingAndPaymentDate);
        groupForm.setReceivingAndPaymentTime(receivingAndPaymentTime);

        groupForm.setGroupMemo(groupMemo);

        specificationGroupService.saveAndFlush(groupForm.toEntity());

        groupForm.setDeleted(false);

        specificationGroupService.saveAndFlush(groupForm.toEntity());

        detailForm
                = new SpecificationForm(
                specificationService,
                specificationGroupId);
        detailForm.setDeleted(false);

        List<SpecificationForm> specificationForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        if (specificationForms.size() == 1) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specificationForms.getFirst().getCurrencyId());
            specificationForms.getFirst().setCurrencyId(
                    currencyListForm.getCurrencyId());
            specificationForms.getFirst().setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specificationForms.getFirst().getUnitId());
            specificationForms.getFirst().setUnitId(unitForm.getUnitId());
            specificationForms.getFirst().setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specificationForms.getFirst().getTaxTypeId());
            specificationForms.getFirst().setTaxTypeId(taxTypeForm.getTaxTypeId());
            specificationForms.getFirst().setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specificationForms.getFirst().getTaxRateId());
            specificationForms.getFirst().setTaxRateId(taxRateForm.getTaxRateId());
            specificationForms.getFirst().setTaxRateForm(taxRateForm);
        }

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForm", detailForm);
        model.addAttribute(
                "currencyLists", currencyListService.findAll());
        model.addAttribute(
                "units", unitService.findAll());
        model.addAttribute(
                "taxTypes", taxTypeService.findAll());
        model.addAttribute(
                "taxRates", taxRateService.findAll());
        model.addAttribute("userDetails", userDetails);
        model.addAttribute(
                "accountAndBalanceForm",
                accountAndBalanceForm);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "balanceTypes",
                balanceTypeService.findAll());
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute(
                "groupMemo",
                groupMemo);

        return "spec/createDetail";
    }

    @GetMapping(value = "/spec/edit/detail/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/{receivingAndPaymentDate}/{receivingAndPaymentTime}")
    String cancelEditSpecification(
            @PathVariable("specificationGroupId") Long specificationGroupId,
//      @PathVariable("specificationId") Long specificationId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @PathVariable("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @RequestParam(name = "groupMemo", required = false) String groupMemo,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @ModelAttribute BalanceType balanceType,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        groupForm.setDeleted(false);
        groupForm.setGroupMemo(group.getGroupMemo());

        specificationGroupService.saveAndFlush(groupForm.toEntity());

        accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountDestinationForm = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);

        shopForm = new ShopForm(shopService);
        shopForm.setShopId(shopId);
        groupForm.setShopId(shopForm.getShopId());
        groupForm.setShopForm(shopForm);

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForm", detailForm);
        model.addAttribute(
                "accountAndBalanceForm", accountAndBalanceForm);
        model.addAttribute(
                "shopForm", shopForm);
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm", accountDestinationForm);
        model.addAttribute(
                "balanceType", balanceType);
        model.addAttribute(
                "currencyLists", currencyListService.findAll());
        model.addAttribute(
                "units", unitService.findAll());
        model.addAttribute(
                "taxTypes", taxTypeService.findAll());
        model.addAttribute(
                "taxRates", taxRateService.findAll());
        model.addAttribute("userDetails", userDetails);
//    model.addAttribute("groupMemo", groupMemo);

        return "spec/editGroup";
    }

    @PostMapping(value = "/spec/edit/detail/{specificationGroupId}/{specificationId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}")
    String editSpecification(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("specificationId") Long specificationId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
//      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
//      @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @ModelAttribute BalanceType balanceType,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        groupForm.setDeleted(false);
        groupForm.setGroupMemo(group.getGroupMemo());

        specificationGroupService.saveAndFlush(groupForm.toEntity());

        accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountDestinationForm = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);

        Specification specification
                = specificationService.findBySpecificationGroupIdAndSpecificationIdAndUsernameAndDeleted(
                specificationGroupId,
                specificationId,
                userDetails.getUsername(),
                false);

        detailForm = detailForm.setSpecificationToForm(
                specification);

        shopForm = new ShopForm(shopService);
        shopForm.setShopId(shopId);
        groupForm.setShopId(shopForm.getShopId());
        groupForm.setShopForm(shopForm);

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForm", detailForm);
        model.addAttribute(
                "accountAndBalanceForm", accountAndBalanceForm);
        model.addAttribute(
                "shopForm", shopForm);
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm", accountDestinationForm);
        model.addAttribute(
                "balanceType", balanceType);
        model.addAttribute(
                "currencyLists", currencyListService.findAll());
        model.addAttribute(
                "units", unitService.findAll());
        model.addAttribute(
                "taxTypes", taxTypeService.findAll());
        model.addAttribute(
                "taxRates", taxRateService.findAll());
        model.addAttribute("userDetails", userDetails);

        return "spec/editDetail";
    }

    @PostMapping(value = "/spec/save/group/{userId}")
    String saveGroup(
            @PathVariable("userId") Long userId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute Specification specification,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        Long balanceTypeId = groupForm.getBalanceTypeId();
        String groupMemo = groupForm.getGroupMemo();

        if (groupForm.getSpecificationGroupId() == null) {
            SpecificationGroup group
                    = new SpecificationGroup();

            AccountSourceForm accountSourceForm
                    = new AccountSourceForm(
                    accountSourceService)
                    .setAccountSourceFormByDB(
                            Long.parseLong("1"));
            accountAndBalanceForm.setAccountSourceId(
                    accountSourceForm.getAccountSourceId());
            accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

            AccountDestinationForm accountDestinationForm
                    = new AccountDestinationForm(
                    accountDestinationService)
                    .setAccountDestinationFormByDB(
                            Long.parseLong("1"));
            accountAndBalanceForm.setAccountDestinationId(
                    accountDestinationForm.getAccountDestinationId());
            accountAndBalanceForm.setAccountDestinationForm(
                    accountDestinationForm);

            group.setAccountAndBalanceId(
                    accountAndBalanceForm.getAccountAndBalanceId());

            group.setUserId(userId);
            group.setDeleted(false);
            groupForm = groupForm.setSpecificationGroupToForm(group);

            groupForm.setAccountAndBalanceForm(accountAndBalanceForm);

            accountAndBalanceService.saveAndFlush(
                    accountAndBalanceForm.toEntity());
        }

        groupForm.setAccountAndBalanceForm(accountAndBalanceForm);

        groupForm.setReceivingAndPaymentDate(receivingAndPaymentDate);
        groupForm.setReceivingAndPaymentTime(receivingAndPaymentTime);

        Shop shop = shopService.findById(shopForm.getShopId());
        groupForm.setShopId(shop.getShopId());
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeForm.getBalanceTypeId());
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        groupForm.setDeleted(false);

        SpecificationGroup specGroup = groupForm.toEntity();
        specificationGroupService
                .saveAndFlush(specGroup);

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                groupForm.getSpecificationGroupId(),
                null,
                false);

        balanceType
                = balanceTypeService.findByBalanceTypeId(balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        for (var specForm : detailForms) {
            if (specForm.getTaxTypeForm().getTaxTypeId() == 1) {
                if (specForm.getCurrencyListForm().getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) specForm.getTaxRateForm().getTaxRate() / 100)));
                }
            } else {
                if (specForm.getCurrencyListForm().getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        model.addAttribute("specificationGroupForm", groupForm);
        model.addAttribute("specificationForms", detailForms);
        model.addAttribute("currencyLists", currencyListService.findAll());
        model.addAttribute("units", unitService.findAll());
        model.addAttribute("taxTypes", taxTypeService.findAll());
        model.addAttribute("taxRates", taxRateService.findAll());
        model.addAttribute(
                "accountAndBalanceId",
                groupForm.getAccountAndBalanceForm().getAccountAndBalanceId());
        model.addAttribute("shopForm", shopService.findById(groupForm.getShopId()));
        model.addAttribute(
                "accountSourceForm",
                new AccountSourceForm(
                        accountSourceService)
                        .setAccountSourceFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountSourceId()));
        model.addAttribute(
                "accountDestinationForm",
                new AccountDestinationForm(
                        accountDestinationService)
                        .setAccountDestinationFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountDestinationId()));
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("balanceTypes", balanceTypeService.findAll());
        model.addAttribute("totalAmount", Math.round(totalAmount));

        return "spec/editGroup";
    }

    @GetMapping(value = "/spec/update/group/{specificationGroupId}/{userId}")
    String cancelUpdateGroup(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
//      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
//      @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute Specification specification,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

//    Long balanceTypeId = groupForm.getBalanceTypeId();
        String groupMemo = groupForm.getGroupMemo();

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        groupForm = groupForm.setSpecificationGroupToForm(group);

        groupForm.setGroupMemo(groupMemo);

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(accountAndBalanceForm);

        accountAndBalanceForm.setAccountSourceId(
                groupForm.getAccountAndBalanceForm().getAccountSourceId());
        accountAndBalanceForm.setAccountDestinationId(
                groupForm.getAccountAndBalanceForm().getAccountDestinationId());
        accountAndBalanceService.saveAndFlush(
                accountAndBalanceForm.toEntity());

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        Shop shop = shopService.findById(shopForm.getShopId());
        groupForm.setShopId(shop.getShopId());
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeForm.getBalanceTypeId());
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        SpecificationGroup specGroup = groupForm.toEntity();
        specificationGroupService
                .saveAndFlush(specGroup);

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                groupForm.getSpecificationGroupId(),
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        model.addAttribute("specificationGroupForm", groupForm);
        model.addAttribute("specificationForms", detailForms);
        model.addAttribute("currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute("taxTypeForm", taxTypeForm);
        model.addAttribute("taxRateForm", taxRateForm);
        model.addAttribute(
                "accountAndBalanceId",
                groupForm.getAccountAndBalanceForm().getAccountAndBalanceId());
        model.addAttribute("shopForm", shopService.findById(groupForm.getShopId()));
        model.addAttribute(
                "accountSourceForm",
                new AccountSourceForm(
                        accountSourceService)
                        .setAccountSourceFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountSourceId()));
        model.addAttribute(
                "accountDestinationForm",
                new AccountDestinationForm(
                        accountDestinationService)
                        .setAccountDestinationFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountDestinationId()));
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("balanceTypes", balanceTypeService.findAll());
        model.addAttribute("selectedBalanceTypeId", groupForm.getBalanceTypeId());
        model.addAttribute("totalAmount", Math.round(totalAmount));

        return "spec/showList";
    }

    @PostMapping(value = "/spec/update/group/{specificationGroupId}/{userId}")
    String updateGroup(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute Specification specification,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

//    Long balanceTypeId = groupForm.getBalanceTypeId();
        String groupMemo = groupForm.getGroupMemo();

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        groupForm = groupForm.setSpecificationGroupToForm(group);

        groupForm.setGroupMemo(groupMemo);

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(accountAndBalanceForm);

        accountAndBalanceForm.setAccountSourceId(
                groupForm.getAccountAndBalanceForm().getAccountSourceId());
        accountAndBalanceForm.setAccountDestinationId(
                groupForm.getAccountAndBalanceForm().getAccountDestinationId());
        accountAndBalanceService.saveAndFlush(
                accountAndBalanceForm.toEntity());

        groupForm.setReceivingAndPaymentDate(receivingAndPaymentDate);
        groupForm.setReceivingAndPaymentTime(receivingAndPaymentTime);

        Shop shop = shopService.findById(shopForm.getShopId());
        groupForm.setShopId(shop.getShopId());
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeForm.getBalanceTypeId());
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        SpecificationGroup specGroup = groupForm.toEntity();
        specificationGroupService
                .saveAndFlush(specGroup);

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                groupForm.getSpecificationGroupId(),
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        model.addAttribute("specificationGroupForm", groupForm);
        model.addAttribute("specificationForms", detailForms);
        model.addAttribute("currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute("taxTypeForm", taxTypeForm);
        model.addAttribute("taxRateForm", taxRateForm);
        model.addAttribute(
                "accountAndBalanceId",
                groupForm.getAccountAndBalanceForm().getAccountAndBalanceId());
        model.addAttribute("shopForm", shopService.findById(groupForm.getShopId()));
        model.addAttribute(
                "accountSourceForm",
                new AccountSourceForm(
                        accountSourceService)
                        .setAccountSourceFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountSourceId()));
        model.addAttribute(
                "accountDestinationForm",
                new AccountDestinationForm(
                        accountDestinationService)
                        .setAccountDestinationFormByDB(
                                groupForm
                                        .getAccountAndBalanceForm()
                                        .getAccountDestinationId()));
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("balanceTypes", balanceTypeService.findAll());
        model.addAttribute("selectedBalanceTypeId", groupForm.getBalanceTypeId());
        model.addAttribute("totalAmount", Math.round(totalAmount));

        return "spec/editGroup";
    }

    @GetMapping(value = "/spec/save/create/detail/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}")
    String cancelSaveCreateDetail(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        String groupMemo = groupForm.getGroupMemo();

        detailForm.setSpecificationGroupId(
                specificationGroupId);

        Shop shop = shopService.findById(shopId);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopId(shop.getShopId());
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        AccountAndBalance accountAndBalance
                = (AccountAndBalance) accountAndBalanceService.findByAccountAndBalanceId(accountAndBalanceId);
        groupForm.setAccountAndBalanceId(
                accountAndBalance.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(
                new AccountAndBalanceForm()
                        .setAccountAndBalanceToForm(
                                accountAndBalance));

        groupForm.getAccountAndBalanceForm()
                .setAccountSourceId(accountSourceId);
        accountSourceForm
                = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountAndBalanceForm.setAccountSourceId(accountSourceId);
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        groupForm.getAccountAndBalanceForm()
                .setAccountDestinationId(accountDestinationId);
        accountDestinationForm
                = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationId);
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);

        currencyListForm = new CurrencyListForm(
                currencyListService,
                detailForm.getCurrencyId());
        detailForm.setCurrencyId(
                currencyListForm.getCurrencyId());

        unitForm = new UnitForm(
                unitService)
                .setUnitFormByDB(
                        detailForm.getUnitId());
        detailForm.setUnitId(unitForm.getUnitId());

        taxTypeForm = new TaxTypeForm(
                taxTypeService)
                .setTaxTypeFormByDB(
                        detailForm.getTaxTypeId());
        detailForm.setTaxTypeId(taxTypeForm.getTaxTypeId());

        taxRateForm = new TaxRateForm(
                taxRateService)
                .setTaxRateFormByDB(
                        detailForm.getTaxRateId());
        detailForm.setTaxRateId(taxRateForm.getTaxRateId());

        if (detailForm.getEntryDate() == null) {
            detailForm.setEntryDate(ZonedDateTime.now());
        } else {
            detailForm.setUpdateDate(ZonedDateTime.now());
        }

        detailForm.setDeleted(false);
        specificationService.saveAndFlush(
                detailForm.toEntity());

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        groupForm.setGroupMemo(groupMemo);

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForms", detailForms);
        model.addAttribute("currencyListForm",
                currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute("taxTypeForm", taxTypeForm);
        model.addAttribute("taxRateForm", taxRateForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute(
                "accountAndBalanceForm", accountAndBalanceForm);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "balanceTypes", balanceTypeService.findAll());
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("totalAmount", Math.round(totalAmount));

        return "spec/editDetail";
    }

    @PostMapping(value = "/spec/save/create/detail/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}")
    String saveCreateDetail(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        String groupMemo = groupForm.getGroupMemo();

        detailForm.setSpecificationGroupId(
                specificationGroupId);

        Shop shop = shopService.findById(shopId);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopId(shop.getShopId());
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        AccountAndBalance accountAndBalance
                = (AccountAndBalance) accountAndBalanceService.findByAccountAndBalanceId(accountAndBalanceId);
        groupForm.setAccountAndBalanceId(
                accountAndBalance.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(
                new AccountAndBalanceForm()
                        .setAccountAndBalanceToForm(
                                accountAndBalance));

        groupForm.getAccountAndBalanceForm()
                .setAccountSourceId(accountSourceId);
        accountSourceForm
                = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountAndBalanceForm.setAccountSourceId(accountSourceId);
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        groupForm.getAccountAndBalanceForm()
                .setAccountDestinationId(accountDestinationId);
        accountDestinationForm
                = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationId);
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);

        currencyListForm = new CurrencyListForm(
                currencyListService,
                detailForm.getCurrencyId());
        detailForm.setCurrencyId(
                currencyListForm.getCurrencyId());

        unitForm = new UnitForm(
                unitService)
                .setUnitFormByDB(
                        detailForm.getUnitId());
        detailForm.setUnitId(unitForm.getUnitId());

        taxTypeForm = new TaxTypeForm(
                taxTypeService)
                .setTaxTypeFormByDB(
                        detailForm.getTaxTypeId());
        detailForm.setTaxTypeId(taxTypeForm.getTaxTypeId());

        taxRateForm = new TaxRateForm(
                taxRateService)
                .setTaxRateFormByDB(
                        detailForm.getTaxRateId());
        detailForm.setTaxRateId(taxRateForm.getTaxRateId());

        if (detailForm.getEntryDate() == null) {
            detailForm.setEntryDate(ZonedDateTime.now());
        } else {
            detailForm.setUpdateDate(ZonedDateTime.now());
        }

        detailForm.setDeleted(false);
        detailForm.setSpecificationId(
                specificationService
                        .getNextSpecificationId(
                                specificationGroupId));

        specificationService.saveAndFlush(
                detailForm.toEntity());

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        groupForm.setGroupMemo(groupMemo);

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForms", detailForms);
        model.addAttribute("currencyListForm",
                currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute("taxTypeForm", taxTypeForm);
        model.addAttribute("taxRateForm", taxRateForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute(
                "accountAndBalanceForm", accountAndBalanceForm);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "balanceTypes", balanceTypeService.findAll());
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("totalAmount", Math.round(totalAmount));

        return "spec/editGroup";
    }

    @GetMapping(value = "/spec/save/edit/detail/{specificationGroupId}/{specificationId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/{receivingAndPaymentDate}/{receivingAndPaymentTime}")
    String cancelSaveEditDetail(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("specificationId") Long specificationId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @PathVariable("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
            @PathVariable("receivingAndPaymentTime") LocalDate receivingAndPaymentTime,
            @RequestParam("groupMemo") String groupMemo,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
//      @ModelAttribute String groupMemo,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);

        groupForm.setSpecificationGroupId(specificationGroupId);

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        groupForm.setDeleted(false);
        groupForm.setGroupMemo(group.getGroupMemo());

//    specificationGroupService.saveAndFlushSpecificationGroup(groupForm.toEntity());

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(
                accountAndBalanceForm);

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        ShopForm shopForm = new ShopForm(shopService);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        AccountSourceForm accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountAndBalanceForm.setAccountSourceId(accountSourceId);
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        AccountDestinationForm accountDestinationForm
                = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationId);
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);
        groupForm.setAccountAndBalanceForm(
                accountAndBalanceForm);

        groupForm.setGroupMemo(group.getGroupMemo());

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        groupForm.setGroupMemo(group.getGroupMemo());

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForms", detailForms);
        model.addAttribute(
                "currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute("taxTypeForm", taxTypeForm);
        model.addAttribute("taxRateForm", taxRateForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute(
                "accountAndBalanceForm", accountAndBalanceForm);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "balanceTypes", balanceTypeService.findAll());
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("totalAmount", Math.round(totalAmount));

        return "spec/editGroup";
    }

    @PostMapping(value = "/spec/save/edit/detail/{specificationGroupId}/{specificationId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}")
    String saveEditDetail(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("specificationId") Long specificationId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @RequestParam(name = "groupMemo") String groupMemo,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        groupForm.setReceivingAndPaymentDate(receivingAndPaymentDate);
        groupForm.setReceivingAndPaymentTime(receivingAndPaymentTime);

        groupForm.setDeleted(false);
        groupForm.setGroupMemo(groupMemo);

        specificationGroupService.saveAndFlush(groupForm.toEntity());

        double totalAmount = Double.parseDouble("0.0");

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(
                accountAndBalanceForm);

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        ShopForm shopForm = new ShopForm(shopService);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        AccountSourceForm accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountAndBalanceForm.setAccountSourceId(accountSourceId);
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        AccountDestinationForm accountDestinationForm
                = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationId);
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);
        groupForm.setAccountAndBalanceForm(
                accountAndBalanceForm);

        groupForm.setGroupMemo(group.getGroupMemo());

        currencyListForm = new CurrencyListForm(
                currencyListService,
                detailForm.getCurrencyId());
        detailForm.setCurrencyId(
                currencyListForm.getCurrencyId());

        unitForm = new UnitForm(
                unitService)
                .setUnitFormByDB(
                        detailForm.getUnitId());
        detailForm.setUnitId(unitForm.getUnitId());

        taxTypeForm = new TaxTypeForm(
                taxTypeService)
                .setTaxTypeFormByDB(
                        detailForm.getTaxTypeId());
        detailForm.setTaxTypeId(taxTypeForm.getTaxTypeId());

        taxRateForm = new TaxRateForm(
                taxRateService)
                .setTaxRateFormByDB(
                        detailForm.getTaxRateId());
        detailForm.setTaxRateId(taxRateForm.getTaxRateId());

        if (detailForm.getEntryDate() == null) {
            detailForm.setEntryDate(ZonedDateTime.now());
        } else {
            detailForm.setUpdateDate(ZonedDateTime.now());
        }

        detailForm.setDeleted(false);

        specificationService.updateSpecDetail(detailForm.toEntity());

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        groupForm.setGroupMemo(group.getGroupMemo());

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForms", detailForms);
        model.addAttribute(
                "currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute("taxTypeForm", taxTypeForm);
        model.addAttribute("taxRateForm", taxRateForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute(
                "accountAndBalanceForm", accountAndBalanceForm);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "balanceTypes", balanceTypeService.findAll());
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("totalAmount", Math.round(totalAmount));

        return "spec/editGroup";
    }

    @PostMapping("/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/searchShop")
    String searchShop(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        specificationGroupService.saveAndFlush(groupForm.toEntity());

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        String groupMemo = group.getGroupMemo();

        groupForm.setUserId(userId);

        AccountAndBalance accountAndBalance
                = (AccountAndBalance) accountAndBalanceService.findByAccountAndBalanceId(accountAndBalanceId);

        List<Shop> shops = shopService.findAll();

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        AccountSource accountSource
                = accountSourceService.findByAccountSourceId(accountSourceId);
        AccountDestination accountDestination
                = accountDestinationService.findByAccountDestinationId(accountDestinationId);

        groupForm.setDeleted(false);
        groupForm.setGroupMemo(groupMemo);

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "accountAndBalanceId", accountAndBalanceId);
        model.addAttribute("shops", shops);
        model.addAttribute(
                "balanceTypeForm", groupForm.getBalanceTypeForm());
        model.addAttribute(
                "accountSourceId", accountSourceId);
        model.addAttribute(
                "accountDestinationId", accountDestinationId);
        model.addAttribute(
                "userDetails", userDetails);

        return "/shop/showList";
    }

    @PostMapping("/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/searchAccountSource")
    String searchAccountSource(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            Model model) {

        specificationGroupService.saveAndFlush(groupForm.toEntity());

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        String groupMemo = group.getGroupMemo();

        groupForm.setUserId(userId);

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        ShopForm shopForm = new ShopForm(shopService);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        AccountAndBalance accountAndBalance
                = (AccountAndBalance) accountAndBalanceService.findByAccountAndBalanceId(accountAndBalanceId);

        List<AccountSource> accountSources = accountSourceService.findAll();
        AccountDestinationForm accountDestinationForm
                = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);

        groupForm.setGroupMemo(groupMemo);

        model.addAttribute(
                "accountAndBalanceId", accountAndBalanceId);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "balanceTypeForm", balanceTypeForm);
        model.addAttribute(
                "accountSources", accountSources);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute(
                "userDetails", userDetails);

        return "/account/showSourceList";
    }

    @PostMapping("/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/searchAccountDestination")
    String searchAccountDestination(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        specificationGroupService.saveAndFlush(groupForm.toEntity());

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        String groupMemo = group.getGroupMemo();

        groupForm.setUserId(userId);

        AccountAndBalance accountAndBalance
                = (AccountAndBalance) accountAndBalanceService.findByAccountAndBalanceId(accountAndBalanceId);

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        ShopForm shopForm = new ShopForm(shopService);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopToForm(shop);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        List<BalanceType> balanceTypes = balanceTypeService.findAll();


        AccountSourceForm accountSourceForm
                = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        List<AccountDestination> accountDestinations
                = accountDestinationService.findAll();

        groupForm.setGroupMemo(groupMemo);

        model.addAttribute(
                "accountAndBalanceId", accountAndBalanceId);
        model.addAttribute(
                "shopForm", shopForm);
        model.addAttribute(
                "balanceTypeForm", groupForm.getBalanceTypeForm());
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinations", accountDestinations);
        model.addAttribute(
                "userDetails", userDetails);

        return "/account/showDestinationList";
    }

    //  @GetMapping(value = "/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/{receivingAndPaymentDate}/{receivingAndPaymentTime}/setShop")
    @GetMapping(value = "/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/setShop")
    String setShopToSpecificationGroup(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        String groupMemo = group.getGroupMemo();

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        ShopForm shopForm = new ShopForm(shopService);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        List<BalanceType> balanceTypes = balanceTypeService.findAll();

        AccountAndBalance accountAndBalance
                = accountAndBalanceService.findByAccountAndBalanceId(accountAndBalanceId);
        groupForm.setAccountAndBalanceId(
                accountAndBalance.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceToForm(accountAndBalance);

        groupForm.getAccountAndBalanceForm().setAccountSourceId(accountSourceId);
        AccountSourceForm accountSourceForm
                = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        groupForm.getAccountAndBalanceForm().setAccountSourceForm(
                accountSourceForm);

        groupForm.getAccountAndBalanceForm().setAccountDestinationId(
                accountDestinationId);
        AccountDestinationForm accountDestinationForm
                = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        groupForm.getAccountAndBalanceForm().setAccountDestinationForm(
                accountDestinationForm);

        groupForm.setGroupMemo(groupMemo);

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        model.addAttribute("groupForm", groupForm);
        model.addAttribute(
                "specificationForms", detailForms);
        model.addAttribute(
                "accountAndBalanceId", accountAndBalanceId);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute(
                "balanceTypes", balanceTypes);
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute(
                "taxTypeForm", taxTypeForm);
        model.addAttribute(
                "taxRateForm", taxRateForm);
        model.addAttribute(
                "totalAmount", Math.round(totalAmount));

        if (specificationGroupId == null) {
            return "spec/createGroup";
        } else {
            return "spec/editGroup";
        }
    }

    //  @GetMapping(value = "/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/{receivingAndPaymentDate}/{receivingAndPaymentTime}/setAccountSource")
    @GetMapping(value = "/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/setAccountSource")
    String setAccountSourceToAccountAndBalance(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
//      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
//      @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        String groupMemo = group.getGroupMemo();

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        ShopForm shopForm = new ShopForm(shopService);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        List<BalanceType> balanceTypes = balanceTypeService.findAll();

        AccountAndBalance accountAndBalance
                = accountAndBalanceService.findByAccountAndBalanceId(accountAndBalanceId);
        groupForm.setAccountAndBalanceId(
                accountAndBalance.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceToForm(accountAndBalance);

        groupForm.getAccountAndBalanceForm().setAccountSourceId(accountSourceId);
        AccountSourceForm accountSourceForm
                = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        groupForm.getAccountAndBalanceForm().setAccountSourceForm(
                accountSourceForm);

        groupForm.getAccountAndBalanceForm().setAccountDestinationId(
                accountDestinationId);
        AccountDestinationForm accountDestinationForm
                = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        groupForm.getAccountAndBalanceForm().setAccountDestinationForm(
                accountDestinationForm);

        groupForm.setGroupMemo(groupMemo);

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        model.addAttribute(
                "groupForm", groupForm);
        model.addAttribute(
                "specificationForms", detailForms);
        model.addAttribute(
                "accountAndBalanceId", accountAndBalanceId);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("balanceTypes", balanceTypes);
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute(
                "taxTypeForm", taxTypeForm);
        model.addAttribute(
                "taxRateForm", taxRateForm);
        model.addAttribute(
                "totalAmount", Math.round(totalAmount));

        if (specificationGroupId == null) {
            return "spec/createGroup";
        } else {
            return "spec/editGroup";
        }
    }

    //  @GetMapping(value = "/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/{receivingAndPaymentDate}/{receivingAndPaymentTime}/setAccountDestination",)
    @GetMapping(value = "/{specificationGroupId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}/setAccountDestination")
    String setAccountDestinationToAccountAndBalance(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
//      @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam("receivingAndPaymentDate") LocalDate receivingAndPaymentDate,
//      @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) @RequestParam("receivingAndPaymentTime") LocalTime receivingAndPaymentTime,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute CurrencyListForm currencyListForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            @ModelAttribute UnitForm unitForm,
            @ModelAttribute TaxTypeForm taxTypeForm,
            @ModelAttribute TaxRateForm taxRateForm,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        SpecificationGroup group
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);
        String groupMemo = group.getGroupMemo();

        groupForm.setReceivingAndPaymentDate(group.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(group.getReceivingAndPaymentTime());

        Shop shop = shopService.findById(shopId);
        groupForm.setShopId(shop.getShopId());
        ShopForm shopForm = new ShopForm(shopService);
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        List<BalanceType> balanceTypes = balanceTypeService.findAll();

        groupForm.setAccountAndBalanceId(
                accountAndBalanceId);
        AccountAndBalance accountAndBalance
                = (AccountAndBalance) accountAndBalanceService.findByAccountAndBalanceId(accountAndBalanceId);
        groupForm.setAccountAndBalanceToForm(accountAndBalance);

        groupForm.getAccountAndBalanceForm()
                .setAccountSourceId(
                        accountSourceId);
        AccountSourceForm accountSourceForm
                = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        groupForm.getAccountAndBalanceForm()
                .setAccountSourceForm(
                        accountSourceForm);

        groupForm.getAccountAndBalanceForm()
                .setAccountDestinationId(
                        accountDestinationId);
        AccountDestinationForm accountDestinationForm
                = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        groupForm
                .getAccountAndBalanceForm()
                .setAccountDestinationForm(
                        accountDestinationForm);

        groupForm.setGroupMemo(groupMemo);

        List<SpecificationForm> specificationForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : specificationForms) {
            currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(
                    currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        model.addAttribute("groupForm", groupForm);
        model.addAttribute(
                "specificationForms", specificationForms);
        model.addAttribute(
                "accountAndBalanceId", accountAndBalanceId);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute("userDetails", userDetails);
        model.addAttribute("balanceTypes", balanceTypes);
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "currencyListForm", currencyListForm);
        model.addAttribute("unitForm", unitForm);
        model.addAttribute(
                "taxTypeForm", taxTypeForm);
        model.addAttribute(
                "taxRateForm", taxRateForm);
        model.addAttribute(
                "totalAmount", Math.round(totalAmount));

        if (specificationGroupId == null) {
            return "spec/createGroup";
        } else {
            return "spec/editGroup";
        }
    }

    @PostMapping(value = "/spec/delete/group/{specificationGroupId}/{userId}")
    String deleteGroup(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("userId") Long userId,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        List<Specification> specifications
                = specificationService.findBySpecificationGroupIdAndUserIdAndDeleted(
                specificationGroupId,
                userId,
                false);

        specificationService.saveAllSpecificationsDelete(
                specificationGroupId);

        SpecificationGroup specificationGroup
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId, userId, false);

        specificationGroup.setDeleted(true);

        specificationGroupService
                .saveAndFlush(specificationGroup);

        return "redirect:/spec";
    }

    @PostMapping(value = "/spec/delete/detail/{specificationGroupId}/{specificationId}/{userId}/{accountAndBalanceId}/{shopId}/{balanceTypeId}/{accountSourceId}/{accountDestinationId}")
    String deleteDetail(
            @PathVariable("specificationGroupId") Long specificationGroupId,
            @PathVariable("specificationId") Long specificationId,
            @PathVariable("userId") Long userId,
            @PathVariable("accountAndBalanceId") Long accountAndBalanceId,
            @PathVariable("shopId") Long shopId,
            @PathVariable("balanceTypeId") Long balanceTypeId,
            @PathVariable("accountSourceId") Long accountSourceId,
            @PathVariable("accountDestinationId") Long accountDestinationId,
            @ModelAttribute SpecificationGroupForm groupForm,
            @ModelAttribute SpecificationForm detailForm,
            @ModelAttribute AccountAndBalanceForm accountAndBalanceForm,
            @ModelAttribute UsersExtForm usersExtForm,
            @ModelAttribute ShopForm shopForm,
            @ModelAttribute BalanceTypeForm balanceTypeForm,
            @ModelAttribute AccountSourceForm accountSourceForm,
            @ModelAttribute AccountDestinationForm accountDestinationForm,
            @AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            Model model) {

        double totalAmount = Double.parseDouble("0.0");

        SpecificationGroup specificationGroup
                = specificationGroupService
                .findBySpecificationGroupIdAndUserIdAndDeleted(
                        specificationGroupId,
                        userId,
                        false);

        usersExtForm = userExtsService.findByUserExtToForm(userId);
        groupForm.setUsersExtForm(usersExtForm);

        Shop shop = shopService.findById(shopForm.getShopId());
        groupForm.setShopId(shop.getShopId());
        shopForm = shopForm.setShopToForm(shop);
        groupForm.setShopForm(shopForm);

        BalanceType balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        groupForm.setReceivingAndPaymentDate(
                specificationGroup.getReceivingAndPaymentDate());
        groupForm.setReceivingAndPaymentTime(
                specificationGroup.getReceivingAndPaymentTime());

        accountSourceForm = new AccountSourceForm(
                accountSourceService)
                .setAccountSourceFormByDB(
                        accountSourceId);
        accountAndBalanceForm.setAccountSourceId(accountSourceId);
        accountAndBalanceForm.setAccountSourceForm(accountSourceForm);

        accountDestinationForm = new AccountDestinationForm(
                accountDestinationService)
                .setAccountDestinationFormByDB(
                        accountDestinationId);
        accountAndBalanceForm.setAccountDestinationId(
                accountDestinationId);
        accountAndBalanceForm.setAccountDestinationForm(
                accountDestinationForm);

        groupForm.setAccountAndBalanceId(
                accountAndBalanceForm.getAccountAndBalanceId());
        groupForm.setAccountAndBalanceForm(
                accountAndBalanceForm);

        groupForm.setBalanceTypeForm(balanceTypeForm);

        groupForm.setGroupMemo(
                specificationGroup.getGroupMemo());

        groupForm.setEntryDate(
                specificationGroup.getEntryDate());
        groupForm.setUpdateDate(
                specificationGroup.getUpdateDate());

        Specification specification
                = specificationService
                .findBySpecificationGroupIdAndSpecificationIdAndUsernameAndDeleted(
                        specificationGroupId,
                        specificationId,
                        userDetails.getUsername(),
                        false);
        detailForm = detailForm.setSpecificationToForm(
                specification);
        specificationService.updateSpecDetailDeleted(detailForm.toEntity());

        List<SpecificationForm> detailForms
                = specificationService.findBySpecificationGroupIdOrderBySpecificationIdDescToForm(
                specificationGroupId,
                userId,
                false);

        for (var specForm : detailForms) {
            CurrencyListForm currencyListForm = new CurrencyListForm(
                    currencyListService,
                    specForm.getCurrencyId());
            specForm.setCurrencyId(currencyListForm.getCurrencyId());
            specForm.setCurrencyListForm(currencyListForm);

            UnitForm unitForm = new UnitForm(
                    unitService)
                    .setUnitFormByDB(
                            specForm.getUnitId());
            specForm.setUnitId(unitForm.getUnitId());
            specForm.setUnitForm(unitForm);

            TaxTypeForm taxTypeForm = new TaxTypeForm(
                    taxTypeService)
                    .setTaxTypeFormByDB(
                            specForm.getTaxTypeId());
            specForm.setTaxTypeId(taxTypeForm.getTaxTypeId());
            specForm.setTaxTypeForm(taxTypeForm);

            TaxRateForm taxRateForm = new TaxRateForm(
                    taxRateService)
                    .setTaxRateFormByDB(
                            specForm.getTaxRateId());
            specForm.setTaxRateId(taxRateForm.getTaxRateId());
            specForm.setTaxRateForm(taxRateForm);

            if (taxTypeForm.getTaxTypeId() == 1) {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity()
                            * (1.0 + ((double) taxRateForm.getTaxRate() / 100)));
                }
            } else {
                if (currencyListForm.getCurrencyId() == 1) {
                    totalAmount += (specForm.getPrice()
                            * specForm.getQuantity());
                }
            }
        }

        balanceType
                = balanceTypeService.findByBalanceTypeId(
                balanceTypeId);
        groupForm.setBalanceTypeId(balanceType.getBalanceTypeId());
        groupForm.setBalanceTypeToForm(balanceType);

        model.addAttribute(
                "specificationGroupForm", groupForm);
        model.addAttribute(
                "specificationForms", detailForms);
        model.addAttribute(
                "balanceTypes", balanceTypeService.findAll());
        model.addAttribute(
                "selectedBalanceTypeId",
                groupForm.getBalanceTypeId());
        model.addAttribute(
                "accountAndBalanceId", accountAndBalanceId);
        model.addAttribute("shopForm", shopForm);
        model.addAttribute(
                "accountSourceForm", accountSourceForm);
        model.addAttribute(
                "accountDestinationForm",
                accountDestinationForm);
        model.addAttribute(
                "totalAmount", Math.round(totalAmount));

        return "spec/editGroup";
    }
}
