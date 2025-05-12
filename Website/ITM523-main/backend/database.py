import mysql.connector

def create_database():
    cnx = mysql.connector.connect(
        host = "127.0.0.1",
        user = "root",
        password = "12345xyzXYZ"
    )
    cursor = cnx.cursor()
    cursor.execute("CREATE DATABASE IF NOT EXISTS ITM523_Inventory_System")
    cursor.execute('USE ITM523_Inventory_System')
    cnx.close()


def connect_database():
    cnx = mysql.connector.connect(
        host = "127.0.0.1",
        user = "root",
        password = "12345xyzXYZ",
        database  = "ITM523_Inventory_System"
    )
    return cnx
    

def initialize_tables():
    cnx = connect_database()
    cursor = cnx.cursor()

    cursor.execute('''
    CREATE TABLE IF NOT EXISTS IMS_Employees (
        Employee_Id INT AUTO_INCREMENT PRIMARY KEY,
        username VARCHAR(50) UNIQUE NOT NULL,
        password VARCHAR(255) NOT NULL,
        role ENUM('admin', 'employee') NOT NULL,
        firstName VARCHAR(50) NOT NULL,
        lastName VARCHAR(50) NOT NULL,
        dateOfJoining DATE NOT NULL,
        contactNo BIGINT NOT NULL
    )
    ''')

    cursor.execute('''
    CREATE TABLE IF NOT EXISTS IMS_Products (
        Product_Name VARCHAR(255) PRIMARY KEY,
        Company_Name VARCHAR(255) UNIQUE NOT NULL,
        Product_Model_Name VARCHAR(255) NOT NULL,
        Stocks BIGINT NOT NULL,
        Price_Per_Unit BIGINT NOT NULL,
        Total_Price BIGINT AS (Stocks * Price_Per_Unit) STORED

    )
    ''')

    cursor.execute('''
    CREATE TABLE IF NOT EXISTS IMS_Invoices (
        Invoice_Number INT(255) AUTO_INCREMENT,
        Product_Name VARCHAR(255) NOT NULL,
        Customer_Name VARCHAR(255) NOT NULL,
        Company_Name VARCHAR(255) NOT NULL,
        Product_Model_Name VARCHAR(255) NOT NULL,
        Stocks BIGINT NOT NULL,
        Price_Per_Unit BIGINT NOT NULL,
        Total_Price BIGINT AS (Stocks * Price_Per_Unit) STORED,
        Invoice_Date DATETIME DEFAULT CURRENT_TIMESTAMP,
        PRIMARY KEY (Invoice_Number, Product_Name)
    )
    ''')
    
    cnx.commit()
    cnx.close()


if __name__ == "__main__":
    create_database()
    initialize_tables()
    print("Database table initiated now.")