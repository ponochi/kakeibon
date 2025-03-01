--[users]========================================================================================
INSERT INTO users(username, password, role_name, enabled)
VALUES ('juiceA',
-- VALUES ((SELECT (COALESCE(MAX(kpu.user_id), 0) + 1) AS user_id FROM kp.users kpu), 'juiceA',
        '{bcrypt}$2a$10$mekheplkOdsT5v5VLdF.heNd60EEXT3JyVU9qpq.DscBxmMkdutOa',
        'ADMIN', true); -- password: test,
--[users_ext]========================================================================================
INSERT INTO users_ext(user_id, last_name, first_name, email, birth_day,
                      phone_number, entry_date, update_date)
VALUES ((SELECT MAX(user_id) AS user_id FROM users), 'Foo1', 'Bar1',
        'juiceA@example.com', '1971-12-31 00:00:00',
        '00011112222',
        '2024-07-19 11:22:33.123456+09', NULL); -- password: test

INSERT INTO users(username, password, role_name, enabled)
VALUES ('juiceB',
-- VALUES ((SELECT (COALESCE(MAX(kpu.user_id), 0) + 1) AS user_id FROM kp.users kpu), 'juiceB',
        '{bcrypt}$2a$10$yVqiUjrYj5jPMMZ0/M2xh.J6PZqiONu4QT3oB4ZNuF/z1RQX.qLE2',
        'USER', true); -- password: test,
--[users_ext]========================================================================================
INSERT INTO users_ext(user_id, last_name, first_name, email, birth_day,
                      phone_number, entry_date, update_date)
VALUES ((SELECT MAX(user_id) AS user_id FROM users), 'Foo2', 'Bar2',
        'juiceB@example.com', '1970-01-01 00:00:00',
        '00011112222',
        '2024-07-20 22:33:44.789012+09', NULL); -- password: test

--[first_class]=================================================================================
--[支出]===========================================================================================
INSERT INTO first_class(user_id, first_class_name)
SELECT user_id, '支出' FROM users WHERE username='juiceA';
--[収入]===========================================================================================
INSERT INTO first_class(user_id, first_class_name)
SELECT user_id, '収入' FROM users WHERE username='juiceA';
--[振替]===========================================================================================
INSERT INTO first_class(user_id, first_class_name)
SELECT user_id, '振替' FROM users WHERE username='juiceA';

--[second_class]================================================================================
--[支出]===========================================================================================
--[支出] 食費 -------------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '食費' FROM users WHERE username='juiceA';
--[支出] 日用品 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '日用品' FROM users WHERE username='juiceA';
--[支出] 交通費 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '交通費' FROM users WHERE username='juiceA';
--[支出] 医療費 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '医療費' FROM users WHERE username='juiceA';
--[支出] 保険料 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '保険料' FROM users WHERE username='juiceA';
--[支出] 水道光熱費 -------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '水道光熱費' FROM users WHERE username='juiceA';
--[支出] 通信費 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '通信費' FROM users WHERE username='juiceA';
--[支出] 教育費 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '教育費' FROM users WHERE username='juiceA';
--[支出] 交際費 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '交際費' FROM users WHERE username='juiceA';
--[支出] 娯楽費 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '娯楽費' FROM users WHERE username='juiceA';
--[支出] 大型出費 ---------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '大型出費' FROM users WHERE username='juiceA';
--[支出] 年金 -------------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, '年金' FROM users WHERE username='juiceA';
--[支出] その他 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 1, 'その他' FROM users WHERE username='juiceA';
--[収入]===========================================================================================
--[収入] 給与・報酬 -------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 2, '給与・報酬' FROM users WHERE username='juiceA';
--[収入] 賞与 -------------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 2, '賞与' FROM users WHERE username='juiceA';
--[収入] 副業 -------------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 2, '副業' FROM users WHERE username='juiceA';
--[収入] 年金 -------------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 2, '年金' FROM users WHERE username='juiceA';
--[収入] 臨時収入 ---------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 2, '臨時収入' FROM users WHERE username='juiceA';
--[収入] その他 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 2, 'その他' FROM users WHERE username='juiceA';
--[振替]===========================================================================================
--[振替] 普通預貯金 -------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 3, '普通預貯金' FROM users WHERE username='juiceA';
--[振替] 定期預貯金 -------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 3, '定期預貯金' FROM users WHERE username='juiceA';
--[振替] 投資 -------------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 3, '投資' FROM users WHERE username='juiceA';
--[振替] 投機 -------------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 3, '投機' FROM users WHERE username='juiceA';
--[振替] ショッピング -----------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 3, 'ショッピング' FROM users WHERE username='juiceA';
--[振替] その他 -----------------------------------------------------------------------------------
INSERT INTO second_class(user_id, first_class_id, second_class_name)
SELECT user_id, 3, 'その他' FROM users WHERE username='juiceA';

--[second_class_by_order]=======================================================================
--[支出]===========================================================================================
--[支出] 食費 -------------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 1, 1, 1, false FROM users WHERE username='juiceA';
--[支出] 日用品 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 2, 1, 2, false FROM users WHERE username='juiceA';
--[支出] 交通費 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 3, 1, 3, false FROM users WHERE username='juiceA';
--[支出] 医療費 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 4, 1, 4, false FROM users WHERE username='juiceA';
--[支出] 保険料 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 5, 1, 5, false FROM users WHERE username='juiceA';
--[支出] 水道光熱費 -------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 6, 1, 6, false FROM users WHERE username='juiceA';
--[支出] 通信費 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 7, 1, 7, false FROM users WHERE username='juiceA';
--[支出] 教育費 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 8, 1, 8, false FROM users WHERE username='juiceA';
--[支出] 交際費 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 9, 1, 9, false FROM users WHERE username='juiceA';
--[支出] 娯楽費 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 10, 1, 10, false FROM users WHERE username='juiceA';
--[支出] 大型出費 ---------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 11, 1, 11, false FROM users WHERE username='juiceA';
--[支出] 年金 -------------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 12, 1, 12, false FROM users WHERE username='juiceA';
--[支出] その他 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 13, 1, 13, false FROM users WHERE username='juiceA';
--[収入]===========================================================================================
--[収入] 給与・報酬 -------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 14, 2, 1, false FROM users WHERE username='juiceA';
--[収入] 賞与 -------------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 15, 2, 2, false FROM users WHERE username='juiceA';
--[収入] 副業 -------------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 16, 2, 3, false FROM users WHERE username='juiceA';
--[収入] 年金 -------------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 17, 2, 4, false FROM users WHERE username='juiceA';
--[収入] 臨時収入 ---------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 18, 2, 5, false FROM users WHERE username='juiceA';
--[収入] その他 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 19, 2, 6, false FROM users WHERE username='juiceA';
--[振替]===========================================================================================
--[振替] 普通預貯金 -------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 20, 3, 1, false FROM users WHERE username='juiceA';
--[振替] 定期預貯金 -------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 21, 3, 2, false FROM users WHERE username='juiceA';
--[振替] 投資 -------------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 22, 3, 4, false FROM users WHERE username='juiceA';
--[振替] 投機 -------------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 23, 3, 5, false FROM users WHERE username='juiceA';
--[振替] ショッピング -----------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 24, 3, 6, false FROM users WHERE username='juiceA';
--[振替] その他 -----------------------------------------------------------------------------------
INSERT INTO second_class_by_order(user_id, second_class_id, first_class_id, order_number, disabled)
SELECT user_id, 25, 3, 7, false FROM users WHERE username='juiceA';

--[third_class]=================================================================================
--[支出]===========================================================================================
--[支出] 食費 -------------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '食料品' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '飲料' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '酒類' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '菓子' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '外食' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '朝食' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '昼食' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '夕食' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '夜食' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, '間食' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,1, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 日用品 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,2, 1, '消耗品' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,2, 1, '洗剤' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,2, 1, 'トイレットペーパー' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,2, 1, 'ティッシュペーパー' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,2, 1, 'キッチンペーパー' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,2, 1, '文房具' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,2, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 交通費 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, '電車' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, 'バス' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, 'タクシー' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, '飛行機' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, '自動車 (ガソリン代等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, '自転車 (駐輪代等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, '徒歩 (通行料等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, '交通系ICカード (チャージ等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,3, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 医療費 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,4, 1, '病院' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,4, 1, '薬' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,4, 1, '衛生用品' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,4, 1, '食品 (特定保健用食品等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,4, 1, '飲料 (特定保健用飲料等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,4, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 保険料 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,5, 1, '国民健康保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,5, 1, '生命保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,5, 1, '医療保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,5, 1, '終身保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,5, 1, '自動車保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,5, 1, '火災保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,5, 1, '家財保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,5, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 水道光熱費 -------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,6, 1, '電気' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,6, 1, 'ガス' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,6, 1, '水道' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,6, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 通信費 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, '携帯電話' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, '固定電話' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, '固定回線 (インターネット回線等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, 'インターネット (プロバイダ料金等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, 'オンラインサービス (クラウドサービス等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, 'サブスクリプション (動画配信サービス等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, 'テレビ (CATV・衛星放送料金等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, 'ラジオ・ネットラジオ (受信料等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, '宅配便 (宅配料金等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, '郵便 (郵便料金等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,7, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 教育費 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,8, 1, '学費' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,8, 1, '塾代' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,8, 1, 'オンラインスクール代' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,8, 1, '教材費' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,8, 1, '新聞代' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,8, 1, '図書費 (参考書等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,8, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 交際費 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,9, 1, '友人' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,9, 1, '恋人' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,9, 1, '家族・パートナー' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,9, 1, '同僚' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,9, 1, '上司' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,9, 1, '部下・後輩' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,9, 1, '取引先' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,9, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 娯楽費 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, '映画' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, '音楽' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, '演劇' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, '美術館' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, '博物館' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, '遊園地' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, 'スポーツ観戦' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, '国内旅行' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, '海外旅行' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, 'ゲーム' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,10, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 大型出費 ---------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, '白物家電' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, 'パソコン関連' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, 'アプリ購入　(買い切り)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, 'アプリ購入　(サブスクリプション)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, 'デジタルガジェット (スマートフォン等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, 'その他家電' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, '家具' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, '什器 (照明器具等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, '家庭用品' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, '自動車 (新車購入等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, 'バイク (新車購入等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, '自転車 (新車購入等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, '冠婚葬祭 (結婚式費用等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,11, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] 年金 -------------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,12, 1, '国民年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,12, 1, '厚生年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,12, 1, '共済年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,12, 1, 'その他' FROM users WHERE username='juiceA';
--[支出] その他 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,13, 1, '電子マネー　(チャージ等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,13, 1, '使途不明金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,13, 1, 'ATM手数料' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,13, 1, '銀行手数料' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,13, 1, '振込手数料' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,13, 1, '未分類' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,13, 1, 'その他' FROM users WHERE username='juiceA';
--[収入]===========================================================================================
--[収入] 給与・報酬 -------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,14, 2, '給与' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,14, 2, '報酬' FROM users WHERE username='juiceA';
--[収入] 賞与 -------------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,15, 2, '夏期賞与' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,15, 2, '冬期賞与' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,15, 2, '期末賞与' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,15, 2, '臨時賞与' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,15, 2, 'その他' FROM users WHERE username='juiceA';
--[収入] 副業 -------------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,16, 2, '副業' FROM users WHERE username='juiceA';
--[収入] 年金 -------------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,17, 2, '国民年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,17, 2, '基礎年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,17, 2, '厚生年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,17, 2, '共済年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,17, 2, '老齢年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,17, 2, '障害基礎年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,17, 2, '障害厚生年金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,17, 2, 'その他' FROM users WHERE username='juiceA';
--[収入] 臨時収入 ---------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, '医療保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, '生命保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, '終身保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, '自動車保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, '火災保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, '家財保険' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, 'キャッシュバック' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, 'キャンペーン' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,18, 2, 'その他' FROM users WHERE username='juiceA';
--[収入] その他 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,19, 2, 'ATM引出し' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,19, 2, 'その他' FROM users WHERE username='juiceA';
--[振替]===========================================================================================
--[振替] 普通預貯金 -------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,20, 3, '普通預貯金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,20, 3, '定期預貯金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,20, 3, '貯蓄 (簿外・ヘソクリ等)' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,20, 3, '投資' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,20, 3, '投機' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,20, 3, 'ショッピング' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,20, 3, 'その他' FROM users WHERE username='juiceA';
--[振替] 定期預貯金 -------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,21, 3, '普通預貯金' FROM users WHERE username='juiceA';
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,21, 3, '貯蓄 (簿外・ヘソクリ等)' FROM users WHERE username='juiceA';
--[振替] 投資 -------------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,22, 3, '普通預貯金' FROM users WHERE username='juiceA';
--[振替] 投機 -------------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,23, 3, '普通預貯金' FROM users WHERE username='juiceA';
--[振替] ショッピング -----------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,24, 3, '普通預貯金 (返金等)' FROM users WHERE username='juiceA';
--[振替] その他 -----------------------------------------------------------------------------------
INSERT INTO third_class(user_id, second_class_id, first_class_id, third_class_name) SELECT user_id,25, 3, 'その他' FROM users WHERE username='juiceA';


--[third_class_by_order]========================================================================
--[支出]===========================================================================================
--[支出] 食費 -------------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 1, 1, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 2, 1, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 3, 1, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 4, 1, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 5, 1, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 6, 1, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 7, 1, 1, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 8, 1, 1, 8 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 9, 1, 1, 9 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 10, 1, 1, 10 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 11, 1, 1, 11 FROM users WHERE username='juiceA';
--[支出] 日用品 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 12, 2, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 13, 2, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 14, 2, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 15, 2, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 16, 2, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 17, 2, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 18, 2, 1, 7 FROM users WHERE username='juiceA';
--[支出] 交通費 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 19, 3, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 20, 3, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 21, 3, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 22, 3, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 23, 3, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 24, 3, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 25, 3, 1, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 26, 3, 1, 8 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 27, 3, 1, 9 FROM users WHERE username='juiceA';
--[支出] 医療費 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 28, 4, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 29, 4, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 30, 4, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 31, 4, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 32, 4, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 33, 4, 1, 6 FROM users WHERE username='juiceA';
--[支出] 保険料 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 34, 5, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 35, 5, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 36, 5, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 37, 5, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 38, 5, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 39, 5, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 40, 5, 1, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 41, 5, 1, 8 FROM users WHERE username='juiceA';
--[支出] 水道光熱費 -------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 42, 6, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 43, 6, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 44, 6, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 45, 6, 1, 4 FROM users WHERE username='juiceA';
--[支出] 通信費 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 46, 7, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 47, 7, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 48, 7, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 49, 7, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 50, 7, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 51, 7, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 52, 7, 1, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 53, 7, 1, 8 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 54, 7, 1, 9 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 55, 7, 1, 10 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 56, 7, 1, 11 FROM users WHERE username='juiceA';
--[支出] 教育費 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 57, 8, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 58, 8, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 59, 8, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 60, 8, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 61, 8, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 62, 8, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 63, 8, 1, 7 FROM users WHERE username='juiceA';
--[支出] 交際費 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 64, 9, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 65, 9, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 66, 9, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 67, 9, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 68, 9, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 69, 9, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 70, 9, 1, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 71, 9, 1, 8 FROM users WHERE username='juiceA';
--[支出] 娯楽費 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 72, 10, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 73, 10, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 74, 10, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 75, 10, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 76, 10, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 77, 10, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 78, 10, 1, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 79, 10, 1, 8 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 80, 10, 1, 9 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 81, 10, 1, 10 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 82, 10, 1, 11 FROM users WHERE username='juiceA';
--[支出] 大型出費 ---------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 83, 11, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 84, 11, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 85, 11, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 86, 11, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 87, 11, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 88, 11, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 89, 11, 1, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 90, 11, 1, 8 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 91, 11, 1, 9 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 92, 11, 1, 10 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 93, 11, 1, 11 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 94, 11, 1, 12 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 95, 11, 1, 13 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 96, 11, 1, 14 FROM users WHERE username='juiceA';
--[支出] 年金 -------------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 97, 12, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 98, 12, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 99, 12, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 100, 12, 1, 4 FROM users WHERE username='juiceA';
--[支出] その他 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 101, 13, 1, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 102, 13, 1, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 103, 13, 1, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 104, 13, 1, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 105, 13, 1, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 106, 13, 1, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 107, 13, 1, 7 FROM users WHERE username='juiceA';
--[収入]===========================================================================================
--[収入] 給与・報酬 -------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 108, 14, 2, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 109, 14, 2, 2 FROM users WHERE username='juiceA';
--[収入] 賞与 -------------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 110, 15, 2, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 111, 15, 2, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 112, 15, 2, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 113, 15, 2, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 114, 15, 2, 5 FROM users WHERE username='juiceA';
--[収入] 副業 -------------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 115, 16, 2, 1 FROM users WHERE username='juiceA';
--[収入] 年金 -------------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 116, 17, 2, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 117, 17, 2, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 118, 17, 2, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 119, 17, 2, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 120, 17, 2, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 121, 17, 2, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 122, 17, 2, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 123, 17, 2, 8 FROM users WHERE username='juiceA';
--[収入] 臨時収入 ---------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 124, 18, 2, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 125, 18, 2, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 126, 18, 2, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 127, 18, 2, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 128, 18, 2, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 129, 18, 2, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 130, 18, 2, 7 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 131, 18, 2, 8 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 132, 18, 2, 9 FROM users WHERE username='juiceA';
--[収入] その他 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 133, 19, 2, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 134, 19, 2, 2 FROM users WHERE username='juiceA';
--[振替]===========================================================================================
--[振替] 普通預貯金 -------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 135, 20, 3, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 136, 20, 3, 2 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 137, 20, 3, 3 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 138, 20, 3, 4 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 139, 20, 3, 5 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 140, 20, 3, 6 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 141, 20, 3, 7 FROM users WHERE username='juiceA';
--[振替] 定期預貯金 -------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 142, 21, 3, 1 FROM users WHERE username='juiceA';
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 143, 21, 3, 2 FROM users WHERE username='juiceA';
--[振替] 投資 -------------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 144, 22, 3, 1 FROM users WHERE username='juiceA';
--[振替] 投機 -------------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 145, 23, 3, 1 FROM users WHERE username='juiceA';
--[振替] ショッピング -----------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 146, 24, 3, 1 FROM users WHERE username='juiceA';
--[振替] その他 -----------------------------------------------------------------------------------
INSERT INTO third_class_by_order(user_id, third_class_id, second_class_id, first_class_id, order_number)
SELECT user_id, 147, 25, 3, 1 FROM users WHERE username='juiceA';


--[shop]========================================================================================
INSERT INTO shop(shop_name, branch_name, entry_date)
VALUES ('指定なし', '', '1970-01-01T00:00:00');
INSERT INTO shop(shop_name, branch_name, entry_date)
VALUES ('AAAマート', '本店', '2023-08-12T00:00:00');
INSERT INTO shop(shop_name, branch_name, entry_date)
VALUES ('スーパーBBB', 'BBB店', '2024-04-01T00:00:00');

--[account_info]===================================================================================
INSERT INTO account_info(account_name, branch_name, entry_date)
VALUES ('指定なし', '', '1970-01-01T00:00:00');
INSERT INTO account_info(account_name, branch_name, entry_date)
VALUES ('お財布', '', '2023-08-12T00:00:00');
INSERT INTO account_info(account_name, branch_name, entry_date)
VALUES ('AAA銀行', '本店', '2024-04-01T00:00:00');
INSERT INTO account_info(account_name, branch_name, entry_date)
VALUES ('BBBネット銀行', 'ダイヤモンド支店', '2024-04-01T00:00:00');

INSERT INTO balance_type(balance_type_name)
VALUES ('支出');
INSERT INTO balance_type(balance_type_name)
VALUES ('収入');
INSERT INTO balance_type(balance_type_name)
VALUES ('振替');

INSERT INTO tax_type(tax_type_name) VALUES ('外税');
INSERT INTO tax_type(tax_type_name) VALUES ('内税');
INSERT INTO tax_type(tax_type_name) VALUES ('非課税');

INSERT INTO tax_rate(tax_rate) VALUES (8);
INSERT INTO tax_rate(tax_rate) VALUES (10);

INSERT INTO currency_list(currency_name) VALUES ('YEN');
INSERT INTO currency_list(currency_name) VALUES ('USD');
INSERT INTO currency_list(currency_name) VALUES ('EUR');

INSERT INTO unit(unit_name) VALUES ('個');
INSERT INTO unit(unit_name) VALUES ('パック');
INSERT INTO unit(unit_name) VALUES ('本');
INSERT INTO unit(unit_name) VALUES ('袋');
INSERT INTO unit(unit_name) VALUES ('枚');
INSERT INTO unit(unit_name) VALUES ('g');
INSERT INTO unit(unit_name) VALUES ('kg');

INSERT INTO account_and_balance(account_source_id, account_destination_id, entry_date, update_date) VALUES (1, 1, '2024-09-09T00:00:00', null);
INSERT INTO account_and_balance(account_source_id, account_destination_id, entry_date, update_date) VALUES (1, 1, '2024-09-09T00:00:00', null);

INSERT INTO specification_group(user_id, shop_id, receiving_and_payment_date, receiving_and_payment_time, balance_type_id, account_and_balance_id, group_memo, entry_date, update_date) VALUES (2, 2, '2024-09-09', '00:00:00', 1, 1, 'めもめも１', '2024-09-09T00:00:00', null);
INSERT INTO specification_group(user_id, shop_id, receiving_and_payment_date, receiving_and_payment_time, balance_type_id, account_and_balance_id, group_memo, entry_date, update_date) VALUES (2, 3, '2024-09-09', '00:00:00', 1, 2, 'めもめも２', '2024-09-09T00:00:00', null);

INSERT INTO specification(specification_group_id, specification_id, user_id, brand_name, price, currency_id, quantity, unit_id, tax_type_id, tax_rate_id, tax, spec_memo, deleted, entry_date, update_date) VALUES (1, 1, 2, 'DXつけ麺', 299, 1, 1, 1, 2, 1, 22, '', false, '2024-09-09T00:00:00', null);
INSERT INTO specification(specification_group_id, specification_id, user_id, brand_name, price, currency_id, quantity, unit_id, tax_type_id, tax_rate_id, tax, spec_memo, deleted, entry_date, update_date) VALUES (1, 2, 2, 'もちもっち絹厚揚', 89, 1, 1, 1, 1, 1, 7, '', false, '2024-09-09T12:00:00', null);
INSERT INTO specification(specification_group_id, specification_id, user_id, brand_name, price, currency_id, quantity, unit_id, tax_type_id, tax_rate_id, tax, spec_memo, deleted, entry_date, update_date) VALUES (2, 1, 2, 'チキンカツ１', 280, 1, 1, 1, 1, 1, 22, '', false, '2024-09-09T00:00:00', null);
INSERT INTO specification(specification_group_id, specification_id, user_id, brand_name, price, currency_id, quantity, unit_id, tax_type_id, tax_rate_id, tax, spec_memo, deleted, entry_date, update_date) VALUES (2, 2, 2, 'ナポリタン', 220, 1, 1, 1, 2, 1, 16, '', false, '2024-09-09T12:00:00', null);
INSERT INTO specification(specification_group_id, specification_id, user_id, brand_name, price, currency_id, quantity, unit_id, tax_type_id, tax_rate_id, tax, spec_memo, deleted, entry_date, update_date) VALUES (2, 3, 2, 'チキンカツ２', 280, 1, 1, 1, 1, 1, 22, '', false, '2024-09-09T00:00:00', null);
