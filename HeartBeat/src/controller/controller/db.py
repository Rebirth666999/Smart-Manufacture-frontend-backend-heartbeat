


eq_id_name_dict = {}  # 设备id-名称字典
eq_id_link_dict = {}  # 设备id-连接方式字典
op_id_name_dict = {}  # 操作id-名称字典

import mysql.connector
def sql_op():#在数据库中获取到所有设备信息以及所有操作信息，存储在字典中
    # 配置数据库连接参数
    #需要修改
    config = {
        'user': 'root',
        'password': '123456',
        'host': ' 192.168.1.103',
        'database': 'test',
        'raise_on_warnings': True
    }
    # 连接到 MySQL 数据库
    conn = mysql.connector.connect(**config)
    # 创建一个 Cursor 对象并使用它执行 SQL 查询(建立设备id-设备名称和设备id-设备连接方式字典)
    cursor = conn.cursor()

    # 查询数据
    cursor.execute("SELECT * FROM equipment")
    #获取列名
    equipment_names=cursor.column_names
    # 获取查询结果
    rows = cursor.fetchall()
    results=[dict(zip(equipment_names,row)) for row in rows]
    for result in results:
        eq_id_name_dict[result["eq_id"]]=result["eq_name"]
        eq_id_link_dict[result["eq_id"]]=result["eq_link"]
    #建立操作id-设备名称字典
    cursor=conn.cursor()
    cursor.execute("select * from equipment_operation")
    operation_names=cursor.column_names
    rows=cursor.fetchall()
    results=[dict(zip(operation_names,row)) for row in rows]
    for result in results:
        op_id_name_dict[result["op_id"]]=result["op_name"]
    # 关闭 Cursor 和 Connection 对象
    cursor.close()
    conn.close()
    print("数据库数据读取完毕！")
sql_op()
