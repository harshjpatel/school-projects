from flask import Flask, request, render_template, redirect, session, flash, url_for
from database import connect_database
import bcrypt
import secrets
from datetime import datetime, timedelta

app = Flask(__name__, template_folder='../frontend/templates', static_folder='../frontend/static')
app.secret_key = 'your_secret_key'  # Use a strong secret key in production

# Home pafe.
@app.route('/')
def index():
    # The main page : index.html
    return render_template('index.html')


# If admin wants to add an employee.
@app.route('/admin/employees/manage/add', methods=['GET', 'POST'])
# If the credential is not for an admin, it will redirected to the main index.html.
def manage_employees():
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        return redirect('/')

    # Else if the admin credential is correct.
    try:
        print("HELLO")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # To add data into datase using post.
        if request.method == 'POST':
            # Get the firstname of the user.
            firstName = request.form['firstName']
            # Get the lastname of the user.
            lastName = request.form['lastName']
            # Add the date of joining.
            dateOfJoining = request.form['dateOfJoining']
            # Add the contact number
            contactNo = request.form['contactNo']
             # Add a role from the data.
            role = request.form['role']
            # Get the username from the form.
            username = request.form['username']
            # Get the password from the data.
            password = request.form['password']

            # Hash the password
            hashed_password = bcrypt.hashpw(password.encode('utf-8'), bcrypt.gensalt()).decode('utf-8')

            # Adding employees details into employee table, 
            # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-transaction.html
            cursor.execute(
                """INSERT INTO IMS_Employees 
                (username, password, role, firstName, lastName, dateOfJoining, contactNo) 
                VALUES (%s, %s, %s, %s, %s, %s, %s)
                """,
                (
                    username, 
                    hashed_password, 
                    role, firstName, 
                    lastName, 
                    dateOfJoining, 
                    contactNo
                )
            )

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()
            # Products added successfully.
            flash('New employee has been added.', 'success')

        # Retrieve all employees
        cursor.execute("SELECT id, username, role FROM IMS_Employees")
        # Fetch all the employees details and fetch all the data.
        employees = cursor.fetchall()
        # Employee success.
        print(f"Section: employees")
        # https://flask.palletsprojects.com/en/stable/patterns/templateinheritance/
        return render_template('admin_manage_employee.html', employees=employees, username=session.get('username'))
    # Error during the '/admin/products/add' page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        print("exception")
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"Got an error: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        print("finally")
        cnx.close()
    # refdirected to the admin product page if nothing works.
    # if it didn't work antway, redirected it to admin product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/admin')


# Admin can add the product using product name with using GET and POST method with API call -> /admin/products/add/.
# Post call to add products.
@app.route('/admin/products/add', methods=['POST'])
# If the credential is not for an admin, it will redirected to the main index.html.
def admin_manage_product():
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        return redirect('/')

    # Else if the admin credential is correct.
    try:
        print("successful-1")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # To add data into datase using post.
        if request.method == 'POST':
            # Get the firstname of the user.
            productName = request.form['productName']
            # Get the lastname of the user.
            companyName = request.form['companyName']
            # Add the date of joining.
            productModelName = request.form['productModelName']
            # Add the contact number
            stocks = request.form['stocks']
             # Add a role from the data.
            pricePerUnit = request.form['pricePerUnit']

            # Adding products details into product table, 
            # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-transaction.html
            cursor.execute(
                """
                INSERT INTO IMS_Products
                (Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit)
                VALUES (%s, %s, %s, %s, %s)
                """,
                (
                    productName, 
                    companyName, 
                    productModelName, 
                    stocks, 
                    pricePerUnit
                )
            )
            
            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()
            # Products added successfully.
            flash('Product added successfully.', 'success')

        # Retrieve all products
        # cursor.execute
        # (
        #     """
        #     SELECT 
        #         Product_Name, 
        #         Company_Name, 
        #         Product_Model_Name, 
        #         Stocks, 
        #         Price_Per_Unit, 
        #         Stocks * Price_Per_Unit AS totalPrice 
        #     FROM 
        #         IMS_Products
        #     """
        # )

        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit, Stocks * Price_Per_Unit AS totalPrice FROM IMS_Products")

        # Fetch all the product details and fetch all the data.
        admin_products_details = cursor.fetchall()
        # Fetch all the product details.
        print(f"Section: products")

        # https://flask.palletsprojects.com/en/stable/patterns/templateinheritance/
        return render_template('admin_manage_products.html', products=admin_products_details, section="products", username=session.get('username'))
    # Error during the '/admin/products/add' page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        print("Exception")
        flash(f"Got an error: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        print("Finally")
        cnx.close()

    # refdirected to the admin product page if nothing works.
    # if it didn't work antway, redirected it to admin product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/admin/products')


# Admin can edit the product using product name with using GET and POST method with API call -> /admin/products/edit/.
# Post call to edit products.
@app.route('/admin/products/edit/', methods=['GET', 'POST'])
# edit the product from prodict list by admin part by substracting the number of products edited out in the main product table.
def edit_products_sql_query():
    # Get the session role from session.
    print(f"User role: {session.get('role')}")
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
        print("correct-not admin")
        # redirect to the home page.
        return redirect('/')
    
    try:
        # Connect to the database
        print("correct-admin")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()
    
        # Admin can edit the product using product name with using GET and POST method with API call -> /admin/products/edit/.
        # Post call to delete products.
        if request.method == 'GET':
            # Get Successful.
            print("GET successful")
            # product Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Name = request.args.get('productName')
            # company Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            company_Name = request.args.get('productCompany')
            # product's model name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Model_Name =  request.args.get('productModel')
            # product's quantity from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            quantity = request.args.get('quantity')
            # price per unit from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            price_Per_Unit = request.args.get('price')
            # Total price from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            totalPrice = request.args.get('totalPrice')


            # Update the product details by Product_Name as a primary key.
            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-execute.html
            cursor.execute(
                """
                UPDATE IMS_Products 
                SET 
                    Company_Name = %s,
                    Product_Model_Name = %s,
                    Stocks = %s,
                    Price_Per_Unit = %s
                WHERE 
                    Product_Name = %s
                """,
                (company_Name, product_Model_Name, quantity, price_Per_Unit, product_Name)
            )    

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()

             # If no rows got update or executed, https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-rowcount.html
            if cursor.rowcount == 0:
                flash('(FROM ADMIN DASHBOARD) Product is not edited into IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM ADMIN DASHBOARD) Selected product edited successfully into IMS_Products.', 'success')


        elif request.method == 'POST':
            print("POST successful")
            # product Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Name = request.args.get('productName')
            # company Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            company_Name = request.args.get('productCompany')
            # product's model name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Model_Name =  request.args.get('productModel')
            # product's quantity from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            quantity = request.args.get('quantity')
            # price per unit from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            price_Per_Unit = request.args.get('price')
            # Total price from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            totalPrice = request.args.get('totalPrice')


            # Update the product details by Product_Name as a primary key.
            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-execute.html
            cursor.execute(
                """
                UPDATE IMS_Products 
                SET 
                    Company_Name = %s,
                    Product_Model_Name = %s,
                    Stocks = %s,
                    Price_Per_Unit = %s
                WHERE 
                    Product_Name = %s
                """,
                (company_Name, product_Model_Name, quantity, price_Per_Unit, product_Name)
            )    

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()

             # If no rows got update or executed, https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-rowcount.html
            if cursor.rowcount == 0:
                flash('(FROM ADMIN DASHBOARD) Product is not edited into IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM ADMIN DASHBOARD) Selected product edited successfully into IMS_Products.', 'success')
     # Error during the '/admin/products/edit/ page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"(FROM ADMIN DASHBOARD) There is an error while editing the product: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        cnx.close()
    # refdirected to the admin edit product page if nothing works.
    # if it didn't work antway, redirected it to admin edit product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/admin/editProducts')


# Admin can sell the product using product name with using GET and POST method with API call -> /admin/products/sell/.
# Post call to delete products.
@app.route('/admin/products/sell/', methods=['GET', 'POST'])
# edit the product from prodict list by admin part by substracting the number of products sold out.
def sell_products_sql_query():
    # Get the session role from session.
    print(f"User role: {session.get('role')}")
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
        print("correct-not admin")
        # redirect to the home page.
        return redirect('/')
    
    try:
        # Connect to the database
        print("correct-admin")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()
    

        # https://docs.djangoproject.com/en/5.1/ref/request-response/ -> if the request method is GET.
        if request.method == 'GET':
            # Get Successful.
            print("GET successful")
            # Customer Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            customer_Name = request.args.get('customerName')
            # product Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Name = request.args.get('productName')
            # company Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            company_Name = request.args.get('productCompany')
            # product's quantity from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            quantity = request.args.get('quantity')
            # price per unit from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            price_Per_Unit = request.args.get('price')
            # Total price from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            totalPrice = request.args.get('totalPrice')
            # product's model name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Model_Name = request.args.get('productModel')

            #  Update the product by sellinh the total stocks - quantity we sold to the particular customer.
            # cursor.execute(
            #     "UPDATE IMS_Products ",
            #     (quantity, product_Name, company_Name)
            # ) 

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-execute.html
            cursor.execute(
               """ 
                UPDATE IMS_Products 
                SET Stocks = Stocks - %s 
                WHERE Product_Name = %s AND Company_Name = %s
                """,
                (
                    quantity, 
                    product_Name, 
                    company_Name
                )
            )   

            print("done1")

             # If no rows got update or executed,
            if cursor.rowcount == 0:
                flash('(FROM ADMIN DASHBOARD) Product is not there or been deleted already from IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM ADMIN DASHBOARD) Selected product deleted successfully from IMS_Products.', 'success')

            # cursor.execute(
            #     "INSERT INTO IMS_Invoices ",
            #     (product_Name, customer_Name, company_Name, product_Model_Name, quantity, price_Per_Unit)
            # )
            # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-transaction.html

            cursor.execute(
                """
                INSERT INTO IMS_Invoices
                (Product_Name, Customer_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit) 
                VALUES (%s, %s, %s, %s, %s, %s)
                """,
                (
                    product_Name, 
                    customer_Name, 
                    company_Name, 
                    product_Model_Name, 
                    quantity, 
                    price_Per_Unit
                )
            )   

            print("done2")

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()

             # If no rows got update or executed,
            if cursor.rowcount == 0:
                flash('(FROM ADMIN DASHBOARD) Product is not added into IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM ADMIN DASHBOARD) Selected product added successfully into IMS_Products.', 'success')


        # https://docs.djangoproject.com/en/5.1/ref/request-response/ -> if the request method is POST.
        elif request.method == 'POST':
            # Get Successful.
            print("POST successful")
            # Customer Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            customer_Name = request.args.get('customerName')
            # product Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Name = request.args.get('productName')
            # company Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            company_Name = request.args.get('productCompany')
            # product's quantity from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            quantity = request.args.get('quantity')
            # price per unit from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            price_Per_Unit = request.args.get('price')
            # Total price from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            totalPrice = request.args.get('totalPrice')
            # product;s model name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Model_Name = request.args.get('productModel')

            #  Update the product by sellinh the total stocks - quantity we sold to the particular customer.
            # cursor.execute(
            #     "UPDATE IMS_Products ",
            #     (quantity, product_Name, company_Name)
            # ) 

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-execute.html
            cursor.execute(
                """
                UPDATE IMS_Products 
                SET Stocks = Stocks - %s 
                WHERE Product_Name = %s AND Company_Name = %s
                """,
                (
                    quantity, 
                    product_Name, 
                    company_Name
                )
            )   

            print("done1")

             # If no rows got update or executed,
            if cursor.rowcount == 0:
                flash('(FROM ADMIN DASHBOARD) Product is not there or been deleted already from IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM ADMIN DASHBOARD) Selected product deleted successfully from IMS_Products.', 'success')

            # cursor.execute(
            #     "INSERT INTO IMS_Invoices ",
            #     (product_Name, customer_Name, company_Name, product_Model_Name, quantity, price_Per_Unit)
            # )
            # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-transaction.html

            cursor.execute(
                """
                INSERT INTO IMS_Invoices
                (Product_Name, Customer_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit) 
                VALUES (%s, %s, %s, %s, %s, %s)
                """,
                (
                    product_Name, 
                    customer_Name, 
                    company_Name, 
                    product_Model_Name, 
                    quantity, 
                    price_Per_Unit
                )
            )   

            print("done2")

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()

             # If no rows got update or executed,
            if cursor.rowcount == 0:
                flash('(FROM ADMIN DASHBOARD) Product is not added into IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM ADMIN DASHBOARD) Selected product added successfully into IMS_Products.', 'success')
     # Error during the '/admin/products/sell/ page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"(FROM ADMIN DASHBOARD) There is an error while selling the product: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        cnx.close()
    # refdirected to the admin sell product page if nothing works.
    # if it didn't work antway, redirected it to admin sell product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/admin/sellProducts')


# Admin can delete the product using product name with using POST method with API call -> /admin/products/delete/<string:product_name>.
# Post call to delete products.
@app.route('/admin/products/delete/<string:product_name>', methods=['POST'])
# Delete the product from prodict list by admin part.
def admin_delete_products_sql_query(product_name):
    # Get the session role from session.
    print(f"User role: {session.get('roles')}")
    # print product_name
    print("Product's name (to be deleted): ", product_name)
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
        print("correct-not admin")
        # redirect to the home page.
        return redirect('/')
    
    try:
        # Connect to the database
        print("correct-admin")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Print the product_name that admin want to delete
        print(f"(FROM ADMIN ACCESS) We are deleting product -> with product_name is: {product_name}")

        # Delete the employee with the provided product_name.
        cursor.execute("DELETE FROM IMS_Products WHERE Product_Name = %s", (product_name,))
        # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
        cnx.commit()

        # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-rowcount.html
        # If nothing has been deleted, then,
        if cursor.rowcount == 0:
             # Employees not there in the IMS_Employees table.
            flash('(FROM ADMIN ACCESS) Product is not in the IMS_Products table, or either beeen deleted earlier.', 'warning')
        else:
            # Employees been deleted successfully. 
            flash('(FROM ADMIN ACCESS) Product been deleted successfully,', 'success')
     # Error during the /admin/products/delete/<string:product_name> page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"(FROM ADMIN ACCESS) The Error deleting product: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        cnx.close()
    # refdirected to the admin delete product page if nothing works.
    # if it didn't work antway, redirected it to admin delete product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/admin/products')


# Admin can delete the employee using employee ID with using POST method with API call -> /admin/employees/delete/<int:employee_id>.
# Post call to delete employees.
@app.route('/admin/employees/delete/<int:employee_id>', methods=['POST'])
# Delete the employee from employees table by admin part.
def admin_delete_employees_sql_query(employee_id):
    # Get the session role from session.
    print(f"User role: {session.get('role')}")
    # print employee_ids
    print("Employee ID (to be deleted): ", employee_id)
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
            # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
        print("correct-not admin")
        # redirect to the home page.
        return redirect('/')
    
    try:
        # Connect to the database
        print("correct-admin")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Print the employee_id that admin want to delete
        print(f"(FROM ADMIN ACCESS) We are deleting employee -> with employee_id is: {employee_id}")

        # Delete the employee with the provided employee_Id.
        cursor.execute("DELETE FROM IMS_Employees WHERE Employee_Id = %s", (employee_id,))
        # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
        cnx.commit()

        # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-rowcount.html
        # If nothing has been deleted, then,
        if cursor.rowcount == 0:
            # Employees not there in the IMS_Employees table.
            flash('(FROM ADMIN ACCESS) Employee is not in the IMS_Employees table, or either beeen deleted earlier.', 'warning')
        else:
            # Employees been deleted successfully. 
            flash('(FROM ADMIN ACCESS) Employee been deleted successfully,', 'success')
    # Error during the /admin/employees/delete/<int:employee_id> page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"(FROM ADMIN ACCESS) The Error deleting employee: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        cnx.close()
    # refdirected to the admin employee page if nothing works.
    # if it didn't work antway, redirected it to theb index page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/admin')


# Login page with the POST request.
@app.route('/login', methods=['POST'])
def ldap_dashboard():
    #  Get the username from the login page.
    username = request.form['username']
    print("Username: ", username)
    #  Get the password from the login page.
    password = request.form['password']
    print("Password: ", password)
    print("Password1: ", type(password))

    try:
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()
        # Execute the query.
        cursor.execute("SELECT Employee_Id, username, password, role FROM IMS_Employees WHERE username = %s", (username,))
        # This method returns the next row of a result set ( dataset ) as a tuple , or None if no more results available. (https://stackoverflow.com/questions/48143659/cursor-fetchone-returns-none-even-though-a-value-exists)
        # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-fetchone.html
        # It will only retrive the first row from the table.
        employeeUserName = cursor.fetchone()

        # if employeeUserName got find from the cursor from the previous row.
        if employeeUserName:
            # Got the third column value.
            stored_hashed_password = employeeUserName[2]
            print(employeeUserName)

            print(password.encode('utf-8'))
            print(stored_hashed_password.encode('utf-8'))

            # If the password matches without hasing, then
            if password == stored_hashed_password: 
                is_valid = True
            # If the password matches with https://www.npmjs.com/package/bcrypt bcypt.checkpw thing,
            elif bcrypt.checkpw(password.encode('utf-8'), stored_hashed_password.encode('utf-8')):
                is_valid = True
            # Otherwise password false.
            else:
                is_valid = False

            print("Yes")
            # This function checks if a given plaintext password matches a stored hashed password.
            # bcrypt.checkpw(password.encode('utf-8'), stored_hashed_password.encode('utf-8')), https://stackoverflow.com/questions/48761260/bcrypt-encoding-error
            if is_valid:
                print("succeed!")
                # Get the employee_id from the employeeUserName. 
                session['user_id'] = employeeUserName[0]
                session['username'] = employeeUserName[1]
                # Get the role from the employeeUserName.
                session['role'] = employeeUserName[3]
                
                # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
                # from flask import Flask, redirect, url_for
                # app = Flask(__name__)

                # @app.route('/old-page')
                # def old_page():
                #     return redirect(url_for('new_page'))

                # @app.route('/new-page')
                # def new_page():
                #     return 'This is the new page!'
                if employeeUserName[3] == 'admin':
                    print("Admin's credenrials are correct")
                    # return redirected to the admin route.
                    return redirect('/admin')
                # else if the usee is employee
                elif employeeUserName[3] == 'employee':
                    print("Employee's credentials are correct")
                    # return redirected to the employee route.
                    return redirect('/employee')
            # If the password does not match (bcrypt.checkpw returns False), the else block is executed.
            else: # If inot matching password and is_valid is wrong.
                flash("Incorrct Password", 'warning')
        # If the password does not match (bcrypt.checkpw returns False), the else block is executed.
        else: # If there is no single row coming out from the database.
            # It makes it possible to record a message at the end of a request and access it next request and only next request.
            # https://www.geeksforgeeks.org/flask-message-flashing/
            flash("Incorrct Password", 'warning')
    # Error during the login page.
    except Exception as e: 
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"Error in login process: {str(e)}", 'warning')
    # finally to close the connection anyway using finally.
    finally:
        cnx.close()
    # refdirected to the index page if nothing works.
    # if it didn't work antway, redirected it to theb index page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect(url_for('index'))


# Login page with the GET request.
@app.route('/admin', methods=['GET'])
# Admin's Employee dashboard function.
def employee_admin_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    if session.get('role') != 'admin':
        # redirect to the home page.
        return redirect('/')

    try:
        # Connect to the database
        print("Admin successful!")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Retrieve all employees details from IMS_Employees table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Employee_Id, username, role, firstName, lastName, dateOfJoining, ContactNo FROM IMS_Employees")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        admin_employee_details = cursor.fetchall()   # To get the all rows.
        # So, the admin login is successful.
        print("Admin successful-1!")

        # Render the admin_employees.html template with the employees data. A route is a mapping of a URL with a function or any other piece of code to be rendered on the webserver.
        return render_template('admin_manage_employee.html', employees=admin_employee_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        flash(f"Error loading admin employee details: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        cnx.close()


# Go to admin's product page using GET method.
@app.route('/admin/products', methods=['GET'])
# Admin's Product dashboard function.
def admin_product_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        # redirect to the home page.
        return redirect('/')

    try:
        # Connect to the database 
        print("Admin successful-product")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Retrieve all products details from IMS_Products table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit FROM IMS_Products")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        admin_products_details = cursor.fetchall()  
        # To get the all rows.
        print(admin_products_details)
        # So, the admin product is successful.
        print("Admin successful-1!")
        # admin_products_details = cursor.fetchall()  
        print("Admin successful-product-2")

        # Render the admin_manage_products.html template with the admin_products_details data
        return render_template('admin_manage_products.html', products=admin_products_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        # If any exception errors, flash the error and go back to home page.
        print("Admin successful-product-3")
        # print the error message.
        flash(f"Error loading admin dashboard: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        print("Admin successful-product-4")
        cnx.close()


# Go to admin's sell product's page using GET method.
@app.route('/admin/sellProducts', methods=['GET'])
# Admin's selling product dashboard function.
def admin_sellProduct_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        # redirect to the home page.
        return redirect('/')

    try:
        # Connect to the database
        print("Admin successful-sellProduct")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Retrieve all products details from IMS_Products table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit FROM IMS_Products")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        admin_products_details = cursor.fetchall()
        # To get the all rows.
        print(admin_products_details)
        # So, the admin product is successful.
        print("Admin sell products successful-1!")
        # admin_products_details = cursor.fetchall()  
        print("Admin sell products successful-product-2")

        # Render the admin_manage_sellProducts.html template with the admin_products_details data
        return render_template('admin_manage_sellProducts.html', products=admin_products_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        # If any exception errors, flash the error and go back to home page.
        print("Admin successful-product-3")
        # print the error message.
        flash(f"Error loading admin' selling product dashboard: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        print("Admin successful-product-4")
        cnx.close()


# Go to admin's edit product's page using GET method.
@app.route('/admin/editProducts', methods=['GET'])
# Admin's editing product dashboard function.
def admin_editProduct_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        # redirect to the home page.
        return redirect('/')

    try:
        # Connect to the database
        print("Admin successful-editProduct")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Retrieve all products details from IMS_Products table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit FROM IMS_Products")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        admin_products_details = cursor.fetchall()
        # To get the all rows.
        print(admin_products_details)
        # So, the admin product is successful.
        print("Admin edit products successful-1!")
        # admin_products_details = cursor.fetchall()  
        print("Admin edit products successful-product-2")

        # Render the admin_manage_sellProducts.html template with the admin_products_details data
        return render_template('admin_manage_editProducts.html', products=admin_products_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        # If any exception errors, flash the error and go back to home page.
        print("Admin successful-product-3")
        # print the error message.
        flash(f"Error loading admin dashboard: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        print("Admin successful-product-4")
        cnx.close()


# Go to admin's invoices product's page using GET method.
@app.route('/admin/invoices', methods=['GET'])
# Admin's invoices product dashboard function.
def invoicesProduct_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'admin':
        # redirect to the home page.
        return redirect('/')

    try:
        # Connect to the database
        print("Admin successful-invoices")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        print('111')
        # Retrieve all invoices data from IMS_Invoices table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Invoice_Number, Product_Name, Customer_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit, Total_Price, Invoice_Date FROM IMS_Invoices")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        admin_invoices_details = cursor.fetchall()
        # To get the all rows.
        print(admin_invoices_details)
        # So, the admin product is successful.
        print("Admin invoices successful-1!")
        # admin_products_details = cursor.fetchall()  
        print("Admin invoices successful-product-2")

        # Render the admin_manage_invoices.html template with the employees data
        return render_template('admin_manage_invoices.html', invoices=admin_invoices_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        # If any exception errors, flash the error and go back to home page.
        print("Admin successful-product-3")
        # print the error message.
        flash(f"Error loading admin dashboard: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        print("Admin successful-product-4")
        cnx.close()


########################################################################################################################################################################################################################################
########################################################################################################################################################################################################################################

# Go to employee webpage.
@app.route('/employee')
# Employee's invoices product dashboard function.
def employee_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'employee':
        # redirect to the home page.
        return redirect('/')
    # If employee credential is true, then show this message to the admin page.
    try:
        # Connect to the database 
        print("Employee successful-product")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Retrieve all products details from IMS_Products table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit FROM IMS_Products")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        employee_products_details = cursor.fetchall()  
        # To get the all rows.
        print(employee_products_details)
        # So, the admin product is successful.
        print("Employee successful-1!")
        # admin_products_details = cursor.fetchall()  
        print("Employee successful-product-2")
        print(session.get('username'))
        # Render the admin_manage_products.html template with the admin_products_details data
        return render_template('employee_manage_products.html', products=employee_products_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        # If any exception errors, flash the error and go back to home page.
        print("Employee successful-product-3")
        # print the error message.
        flash(f"Error loading admin dashboard: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        print("Employee successful-product-4")
        cnx.close()


# employee can add the product using product name with using GET and POST method with API call -> /employee/products/add/.
# Post call to add products.
@app.route('/employee/products/add', methods=['POST'])
# If the credential is not for an admin, it will redirected to the main index.html.
def employee_manage_product():
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'employee':
        return redirect('/')

    # Else if the admin credential is correct.
    try:
        print("successful-1")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # To add data into datase using post.
        if request.method == 'POST':
            # Get the firstname of the user.
            productName = request.form['productName']
            # Get the lastname of the user.
            companyName = request.form['companyName']
            # Add the date of joining.
            productModelName = request.form['productModelName']
            # Add the contact number
            stocks = request.form['stocks']
             # Add a role from the data.
            pricePerUnit = request.form['pricePerUnit']

            # Adding products details into product table, 
            # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-transaction.html
            cursor.execute(
                """
                INSERT INTO IMS_Products
                (Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit)
                VALUES (%s, %s, %s, %s, %s)
                """,
                (
                    productName, 
                    companyName, 
                    productModelName, 
                    stocks, 
                    pricePerUnit
                )
            )
            
            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()
            # Products added successfully.
            flash('Product added successfully.', 'success')

        # Retrieve all products
        # cursor.execute
        # (
        #     """
        #     SELECT 
        #         Product_Name, 
        #         Company_Name, 
        #         Product_Model_Name, 
        #         Stocks, 
        #         Price_Per_Unit, 
        #         Stocks * Price_Per_Unit AS totalPrice 
        #     FROM 
        #         IMS_Products
        #     """
        # )

        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit, Stocks * Price_Per_Unit AS totalPrice FROM IMS_Products")

        # Fetch all the product details and fetch all the data.
        employee_products_details = cursor.fetchall()
        # Fetch all the product details.
        print(f"Section: products")

        # https://flask.palletsprojects.com/en/stable/patterns/templateinheritance/
        return render_template('employee_manage_products.html', products=employee_products_details, section="products", username=session.get('username'))
    # Error during the '/admin/products/add' page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        print("Exception")
        flash(f"Got an error: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        print("Finally")
        cnx.close()

    # refdirected to the admin product page if nothing works.
    # if it didn't work antway, redirected it to admin product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/employee')


# employee can delete the product using product name with using POST method with API call -> /employee/products/delete/<string:product_name>.
# Post call to delete products.
@app.route('/employee/products/delete/<string:product_name>', methods=['POST'])
# Delete the product from prodict list by admin part.
def employee_delete_products_sql_query(product_name):
    # Get the session role from session.
    print(f"User role: {session.get('roles')}")
    # print product_name
    print("Product's name (to be deleted): ", product_name)
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'employee':
        # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
        print("correct-not employee")
        # redirect to the home page.
        return redirect('/')
    
    try:
        # Connect to the database
        print("correct-employee")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Print the product_name that admin want to delete
        print(f"(FROM EMPLOYEE ACCESS) We are deleting product -> with product_name is: {product_name}")

        # Delete the employee with the provided product_name.
        cursor.execute("DELETE FROM IMS_Products WHERE Product_Name = %s", (product_name,))
        # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
        cnx.commit()

        # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-rowcount.html
        # If nothing has been deleted, then,
        if cursor.rowcount == 0:
             # Employees not there in the IMS_Employees table.
            flash('(FROM EMPLOYEE ACCESS) Product is not in the IMS_Products table, or either beeen deleted earlier.', 'warning')
        else:
            # Employees been deleted successfully. 
            flash('(FROM EMPLOYEE ACCESS) Product been deleted successfully,', 'success')
     # Error during the /admin/products/delete/<string:product_name> page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"(FROM EMPLOYEE ACCESS) The Error deleting product: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        cnx.close()
    # refdirected to the admin delete product page if nothing works.
    # if it didn't work antway, redirected it to admin delete product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/employee')

########################################################################################################################################################################################################################################

# Go to employee's sell product's page using GET method.
@app.route('/employee/sellProducts', methods=['GET'])
# Admin's selling product dashboard function.
def employee_sellProduct_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'employee':
        # redirect to the home page.
        return redirect('/')

    try:
        # Connect to the database
        print("Employee successful-sellProduct")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Retrieve all products details from IMS_Products table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit FROM IMS_Products")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        employee_products_details = cursor.fetchall()
        # To get the all rows.
        print(employee_products_details)
        # So, the admin product is successful.
        print("Employee sell products successful-1!")
        # admin_products_details = cursor.fetchall()  
        print("Employee sell products successful-product-2")

        # Render the admin_manage_sellProducts.html template with the admin_products_details data
        return render_template('employee_manage_sellProducts.html', products=employee_products_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        # If any exception errors, flash the error and go back to home page.
        print("Employee successful-product-3")
        # print the error message.
        flash(f"Error loading employee' selling product dashboard: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        print("Employee successful-product-4")
        cnx.close()


# employee can sell the product using product name with using GET and POST method with API call -> /employee/products/sell/.
# Post call to delete products.
@app.route('/employee/products/sell/', methods=['GET', 'POST'])
# edit the product from prodict list by admin part by substracting the number of products sold out.
def employee_sell_products_sql_query():
    # Get the session role from session.
    print(f"User role: {session.get('role')}")
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'employee':
        # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
        print("correct-not employee")
        # redirect to the home page.
        return redirect('/')
    
    try:
        # Connect to the database
        print("correct-employee")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()
    

        # https://docs.djangoproject.com/en/5.1/ref/request-response/ -> if the request method is GET.
        if request.method == 'GET':
            # Get Successful.
            print("GET successful")
            # Customer Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            customer_Name = request.args.get('customerName')
            # product Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Name = request.args.get('productName')
            # company Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            company_Name = request.args.get('productCompany')
            # product's quantity from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            quantity = request.args.get('quantity')
            # price per unit from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            price_Per_Unit = request.args.get('price')
            # Total price from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            totalPrice = request.args.get('totalPrice')
            # product's model name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Model_Name = request.args.get('productModel')

            #  Update the product by sellinh the total stocks - quantity we sold to the particular customer.
            # cursor.execute(
            #     "UPDATE IMS_Products ",
            #     (quantity, product_Name, company_Name)
            # ) 

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-execute.html
            cursor.execute(
               """ 
                UPDATE IMS_Products 
                SET Stocks = Stocks - %s 
                WHERE Product_Name = %s AND Company_Name = %s
                """,
                (
                    quantity, 
                    product_Name, 
                    company_Name
                )
            )   

            print("done1")

             # If no rows got update or executed,
            if cursor.rowcount == 0:
                flash('(FROM EMPLOYEE DASHBOARD) Product is not there or been deleted already from IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM EMPLOYEE DASHBOARD) Selected product deleted successfully from IMS_Products.', 'success')

            # cursor.execute(
            #     "INSERT INTO IMS_Invoices ",
            #     (product_Name, customer_Name, company_Name, product_Model_Name, quantity, price_Per_Unit)
            # )
            # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-transaction.html

            cursor.execute(
                """
                INSERT INTO IMS_Invoices
                (Product_Name, Customer_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit) 
                VALUES (%s, %s, %s, %s, %s, %s)
                """,
                (
                    product_Name, 
                    customer_Name, 
                    company_Name, 
                    product_Model_Name, 
                    quantity, 
                    price_Per_Unit
                )
            )   

            print("done2")

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()

             # If no rows got update or executed,
            if cursor.rowcount == 0:
                flash('(FROM EMPLOYEE DASHBOARD) Product is not added into IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM EMPLOYEE DASHBOARD) Selected product added successfully into IMS_Products.', 'success')


        # https://docs.djangoproject.com/en/5.1/ref/request-response/ -> if the request method is POST.
        elif request.method == 'POST':
            # Get Successful.
            print("POST successful")
            # Customer Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            customer_Name = request.args.get('customerName')
            # product Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Name = request.args.get('productName')
            # company Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            company_Name = request.args.get('productCompany')
            # product's quantity from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            quantity = request.args.get('quantity')
            # price per unit from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            price_Per_Unit = request.args.get('price')
            # Total price from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            totalPrice = request.args.get('totalPrice')
            # product;s model name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Model_Name = request.args.get('productModel')

            #  Update the product by sellinh the total stocks - quantity we sold to the particular customer.
            # cursor.execute(
            #     "UPDATE IMS_Products ",
            #     (quantity, product_Name, company_Name)
            # ) 

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-execute.html
            cursor.execute(
                """
                UPDATE IMS_Products 
                SET Stocks = Stocks - %s 
                WHERE Product_Name = %s AND Company_Name = %s
                """,
                (
                    quantity, 
                    product_Name, 
                    company_Name
                )
            )   

            print("done1")

             # If no rows got update or executed,
            if cursor.rowcount == 0:
                flash('(FROM EMPLOYEE DASHBOARD) Product is not there or been deleted already from IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM EMPLOYEE DASHBOARD) Selected product deleted successfully from IMS_Products.', 'success')

            # cursor.execute(
            #     "INSERT INTO IMS_Invoices ",
            #     (product_Name, customer_Name, company_Name, product_Model_Name, quantity, price_Per_Unit)
            # )
            # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-transaction.html

            cursor.execute(
                """
                INSERT INTO IMS_Invoices
                (Product_Name, Customer_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit) 
                VALUES (%s, %s, %s, %s, %s, %s)
                """,
                (
                    product_Name, 
                    customer_Name, 
                    company_Name, 
                    product_Model_Name, 
                    quantity, 
                    price_Per_Unit
                )
            )   

            print("done2")

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()

             # If no rows got update or executed,
            if cursor.rowcount == 0:
                flash('(FROM EMPLOYEE DASHBOARD) Product is not added into IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM EMPLOYEE DASHBOARD) Selected product added successfully into IMS_Products.', 'success')
     # Error during the '/admin/products/sell/ page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"(FROM EMPLOYEE DASHBOARD) There is an error while selling the product: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        cnx.close()
    # refdirected to the admin sell product page if nothing works.
    # if it didn't work antway, redirected it to admin sell product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/employee/sellProducts')

########################################################################################################################################################################################################################################

# Go to employee's edit product's page using GET method.
@app.route('/employee/editProducts', methods=['GET'])
# Admin's editing product dashboard function.
def employee_editProduct_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'employee':
        # redirect to the home page.
        return redirect('/')

    try:
        # Connect to the database
        print("Employee successful-editProduct")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        # Retrieve all products details from IMS_Products table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Product_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit FROM IMS_Products")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        employee_products_details = cursor.fetchall()
        # To get the all rows.
        print(employee_products_details)
        # So, the admin product is successful.
        print("Employee edit products successful-1!")
        # admin_products_details = cursor.fetchall()  
        print("Employee edit products successful-product-2")

        # Render the admin_manage_sellProducts.html template with the admin_products_details data
        return render_template('employee_manage_editProducts.html', products=employee_products_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        # If any exception errors, flash the error and go back to home page.
        print("Employee successful-product-3")
        # print the error message.
        flash(f"Error loading admin dashboard: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        print("Employee successful-product-4")
        cnx.close()


# Employee can edit the product using product name with using GET and POST method with API call -> /employee/products/edit/.
# Post call to edit products.
@app.route('/employee/products/edit/', methods=['GET', 'POST'])
# edit the product from prodict list by admin part by substracting the number of products edited out in the main product table.
def employee_edit_products_sql_query():
    # Get the session role from session.
    print(f"User role: {session.get('role')}")
    # Admin's Employee dashboard function.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'employee':
        # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
        print("correct-not admin")
        # redirect to the home page.
        return redirect('/')
    
    try:
        # Connect to the database
        print("correct-admin")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()
    
        # Admin can edit the product using product name with using GET and POST method with API call -> /admin/products/edit/.
        # Post call to delete products.
        if request.method == 'GET':
            # Get Successful.
            print("GET successful")
            # product Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Name = request.args.get('productName')
            # company Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            company_Name = request.args.get('productCompany')
            # product's model name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Model_Name =  request.args.get('productModel')
            # product's quantity from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            quantity = request.args.get('quantity')
            # price per unit from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            price_Per_Unit = request.args.get('price')
            # Total price from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            totalPrice = request.args.get('totalPrice')


            # Update the product details by Product_Name as a primary key.
            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-execute.html
            cursor.execute(
                """
                UPDATE IMS_Products 
                SET 
                    Company_Name = %s,
                    Product_Model_Name = %s,
                    Stocks = %s,
                    Price_Per_Unit = %s
                WHERE 
                    Product_Name = %s
                """,
                (company_Name, product_Model_Name, quantity, price_Per_Unit, product_Name)
            )    

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()

             # If no rows got update or executed, https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-rowcount.html
            if cursor.rowcount == 0:
                flash('(FROM EMPLOYEE DASHBOARD) Product is not edited into IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM EMPLOYEE DASHBOARD) Selected product edited successfully into IMS_Products.', 'success')


        elif request.method == 'POST':
            print("POST successful")
            # product Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Name = request.args.get('productName')
            # company Name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            company_Name = request.args.get('productCompany')
            # product's model name from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            product_Model_Name =  request.args.get('productModel')
            # product's quantity from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            quantity = request.args.get('quantity')
            # price per unit from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            price_Per_Unit = request.args.get('price')
            # Total price from http API call. #https://www.geeksforgeeks.org/using-request-args-for-a-variable-url-in-flask/
            totalPrice = request.args.get('totalPrice')


            # Update the product details by Product_Name as a primary key.
            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-execute.html
            cursor.execute(
                """
                UPDATE IMS_Products 
                SET 
                    Company_Name = %s,
                    Product_Model_Name = %s,
                    Stocks = %s,
                    Price_Per_Unit = %s
                WHERE 
                    Product_Name = %s
                """,
                (company_Name, product_Model_Name, quantity, price_Per_Unit, product_Name)
            )    

            # https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlconnection-commit.html
            cnx.commit()

             # If no rows got update or executed, https://dev.mysql.com/doc/connector-python/en/connector-python-api-mysqlcursor-rowcount.html
            if cursor.rowcount == 0:
                flash('(FROM EMPLOYEE DASHBOARD) Product is not edited into IMS_Products.', 'warning')
            # If rows got update or executed,
            else:
                flash('(FROM EMPLOYEE DASHBOARD) Selected product edited successfully into IMS_Products.', 'success')
     # Error during the '/admin/products/edit/ page.
    except Exception as e:
        # If caught with any exception.
        # https://www.geeksforgeeks.org/flask-message-flashing/
        flash(f"(FROM EMPLOYEE DASHBOARD) There is an error while editing the product: {str(e)}", 'danger')
    # finally to close the connection anyway using finally.
    finally:
        cnx.close()
    # refdirected to the admin edit product page if nothing works.
    # if it didn't work antway, redirected it to admin edit product page.
    # Redirecting to URL in Flask [https://stackoverflow.com/questions/14343812/redirecting-to-url-in-flask]
    return redirect('/employee/editProducts')

########################################################################################################################################################################################################################################

# Go to admin's invoices product's page using GET method.
@app.route('/employee/invoices', methods=['GET'])
# Admin's invoices product dashboard function.
def employee_invoicesProduct_dashboard():
    # Session object allows one to persist certain parameters across requests; if not admin then go to home login page.
    # Admin's Employee dashboard function.
    # If the employeeUserName's role is 'admin' (the role either could be 'admin' or 'employee')
    # from flask import Flask, redirect, url_for
    # app = Flask(__name__)

    # @app.route('/old-page')
    # def old_page():
    #     return redirect(url_for('new_page'))

    # @app.route('/new-page')
    # def new_page():
    #     return 'This is the new page!'
    if session.get('role') != 'employee':
        # redirect to the home page.
        return redirect('/')

    try:
        # Connect to the database
        print("Employee successful-invoices")
        # connect the database <-- conenct with the database.
        cnx = connect_database()
        # A mechanism that enables traversal over the records in a database
        cursor = cnx.cursor()

        print('111')
        # Retrieve all invoices data from IMS_Invoices table.
        # https://dev.mysql.com/doc/connector-python/en/connector-python-example-cursor-select.html
        cursor.execute("SELECT Invoice_Number, Product_Name, Customer_Name, Company_Name, Product_Model_Name, Stocks, Price_Per_Unit, Total_Price, Invoice_Date FROM IMS_Invoices")
        # The method fetches all (or all remaining) rows of a query result set and returns a list of tuples. If no more rows are available, it returns an empty list.
        employee_invoices_details = cursor.fetchall()
        # To get the all rows.
        print(employee_invoices_details)
        # So, the admin product is successful.
        print("Employee invoices successful-1!")
        # admin_products_details = cursor.fetchall()  
        print("Employee invoices successful-product-2")

        # Render the admin_manage_invoices.html template with the employees data
        return render_template('employee_manage_invoices.html', invoices=employee_invoices_details, username=session.get('username'))
    # If any exception errors, flash the error and go back to home page.
    except Exception as e:
        # If any exception errors, flash the error and go back to home page.
        print("Employee successful-product-3")
        # print the error message.
        flash(f"Error loading employee dashboard: {str(e)}", 'danger')
        # Home page.
        return redirect('/')
    # Finally close the database connection.
    finally:
        print("Employee successful-product-4")
        cnx.close()



# Go to logout method.
@app.route('/logout')
# Admin's invoices product dashboard function.
def admin_employee_logout():
    # clea the session.
    session.clear()
    # redirected to home page.
    return redirect('/')


# start the application using the main page.
if __name__ == '__main__':
    # host the application in this page. Its a temporary: 127.0.0.1 (localhost) and port is 5000.
    app.run(debug=True, host='127.0.0.1', port=5000)
