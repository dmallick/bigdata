Pre-Requisite
===============================

Input in MySQL:
+-------------+------------------------+----------------------+
| category_id | category_department_id | category_name        |
+-------------+------------------------+----------------------+
|           1 |                      2 | Football             |
|           2 |                      2 | Soccer               |
|           3 |                      2 | Baseball & Softball  |
|           4 |                      2 | Basketball           |
|           5 |                      2 | Lacrosse             |
|           6 |                      2 | Tennis & Racquet     |
|           7 |                      2 | Hockey               |
+-------------+------------------------+----------------------+

pig -useHCatalog
grunt> A = LOAD 'retail_db.categories' USING org.apache.hive.hcatalog.pig.HCatLoader();
grunt> B = filter A by category_id = 1
grunt> DUMP B;
Result: (1,2,Football)

Query:
============
GROUP_BY_DEPARTMENT = GROUP A BY (category_department_id);
GROUP_BY_DEPARTMENT;

Result:
===========
(2,{(2,2,Soccer),(3,2,Baseball & Softball),(4,2,Basketball),(1,2,Football),(5,2,Lacrosse),(6,2,Tennis & Racquet),(7,2,Hockey),(8,2,More Sports)})
(4,{(17,4,Cleats),(18,4,Men's Footwear),(19,4,Women's Footwear),(20,4,Kids' Footwear),(21,4,Featured Shops),(22,4,Accessories)})

Query:
============
GROUP_BY_DEPARTMENT_NAME = GROUP A BY (category_department_id,category_name);
GROUP_BY_DEPARTMENT_NAME;

Result:
===========
