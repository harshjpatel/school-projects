import Ember from 'ember';

export default Ember.Route.extend({
  model(params) {
    return this.get('store').findRecord('student', params.id);
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isStudent', true);
      console.log('model is '+this.modelFor(this.routeName));
  },
  actions: {
    async saveStudent(sid,name,email,phone,address,mother,father,advisor,dept,books,clasz) {
      var store = this.get('store');
      var currentDepartment
      var currentStudent;
      var currentParent;
      var currentAdvisor;
      let model = this.modelFor(this.routeName);
      console.log("student_name "+name);
      console.log("student_email "+email);
      console.log("student_address "+address);
      console.log("student_phone "+phone);
      console.log("student_ID "+sid);
      console.log("mother "+mother);
      console.log("father "+father);
      console.log("advisor "+advisor);
      console.log("department "+dept);
      console.log('books '+books);
      console.log('classes '+clasz);

      if (model == null) { //no ID given so adding new Student
        //create Student record
        var newStudent = await store.createRecord('student',{
          student_name: name,
          student_email: email,
          student_address: address,
          student_phone: phone,
          student_ID: sid
        }).save();

        //save student first time to get an ID from server
        currentStudent = await store.peekRecord('student', newStudent.get('id'));

        let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
        try {
          //check if department exists
          if (rec.get('firstObject') != undefined) {
            console.log("Department exists.");
            currentDepartment = rec.get('firstObject');
            currentStudent.set('department', currentDepartment);
            currentDepartment.get('students').addObject(currentStudent);
          } else {
            //create new department
            console.log("Creating new department.");
            currentDepartment = store.createRecord('department', {
              dept_name: dept
            });
            currentStudent.set('department', currentDepartment);
            currentDepartment.get('students').addObject(currentStudent);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }

        //add parent
        let rec1 = await store.query("parent", {orderBy: "mother_name", equalTo:mother});
        //check if parent exists
        try {
          if (rec1.get('firstObject') != undefined) {
            console.log("Parent exists.");
            currentParent = rec1.get('firstObject');
            currentStudent.set('parent', currentParent);
            currentParent.get('students').addObject(currentStudent);
          } else {
            //create new parent
            console.log("Creating new parents.");
            currentParent = store.createRecord('parent', {
              mother_name: mother,
              father_name: father
            });
            currentStudent.set('parent',currentParent);
            currentParent.get('students').addObject(currentStudent);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }

        //add advisor
        let rec2 = await store.query("advisor", {orderBy: "advisor_name", equalTo:advisor});
        //check if advisor exists
        try {
          if (rec2.get('firstObject') != undefined) {
            console.log("Advisor exists.");
            currentAdvisor = rec2.get('firstObject');
            currentStudent.set('advisor', currentAdvisor);
            currentAdvisor.get('students').addObject(currentStudent);
          } else {
            //create new advisor
            console.log("Creating new advisor.");
            currentAdvisor = store.createRecord('advisor', {
              advisor_name: advisor
            });
            currentStudent.set('advisor',currentAdvisor);
            currentAdvisor.get('students').addObject(currentStudent);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }

        //add classes
        for (let cla of clasz) {
          let rec3 = await store.query("class", {orderBy: "class_name", equalTo: cla});
          try {
            if (rec3.get('firstObject') != undefined) {
              let cClass = await store.findRecord('class', rec3.get('firstObject').get('id'));
              cClass.get('students').addObject(currentStudent);
              currentStudent.get('classes').pushObject(rec3.get('firstObject'))
              await cClass.save();
            } else {
              //create new class
              console.log("Creating new Class "+cla);
              let tClass = await store.createRecord('class', {
                class_name: cla,
              });
              await tClass.get('students').addObject(currentStudent).save();
              currentStudent.get('classes').pushObject(rec3.get('firstObject')).save();
            }
          } catch(error) {
            console.log("Error querying: "+error);
          }
        }
        //add books
        for (let book of books) {
          let rec4 = await store.query("book", {orderBy: "book_name", equalTo: book});
          try {
            if (rec4.get('firstObject') != undefined) {
              let cBook = await store.findRecord('book', rec4.get('firstObject').get('id'));
              cBook.set('student',currentStudent);
              currentStudent.get('books').pushObject(rec4.get('firstObject')).save();
              await cBook.save();
            } else {
              //create new book
              console.log("Creating new Book "+book);
              let tBook = await store.createRecord('book', {
                book_name: book,
              });
              await tBook.set('student',currentStudent).save();
              currentStudent.get('books').pushObject(rec4.get('firstObject'));
            }
          } catch(error) {
            console.log("Error querying: "+error);
          }
        }

        await currentDepartment.save();
        await currentAdvisor.save();
        await currentParent.save();
        await currentStudent.save();

        console.log("transition");
        this.send('goToStudentList');
    } else { //model not null PATCH
      let student = await this.get('store').findRecord('student',model.id);

      student.set('student_name',name);
      student.set('student_email',email);
      student.set('student_address',address);
      student.set('student_phone',phone);
      student.set('student_ID',sid);
      let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
      try {
        //check if department exists
        if (rec.get('firstObject') != undefined) {
          console.log("Department exists.");
          currentDepartment = rec.get('firstObject');
          student.set('department', currentDepartment);
          currentDepartment.get('students').addObject(student);
        } else {
          //create new department
          console.log("Creating new department.");
          currentDepartment = store.createRecord('department', {
            dept_name: dept
          });
          student.set('department', currentDepartment);
          currentDepartment.get('students').addObject(student);
        }
      } catch (error) {
        console.log("Error querying: "+error);
      }

      //add parent
      let rec1 = await store.query("parent", {orderBy: "mother_name", equalTo:mother});
      //check if parent exists
      try {
        if (rec1.get('firstObject') != undefined) {
          console.log("Parent exists.");
          currentParent = rec1.get('firstObject');
          student.set('parent', currentParent);
          currentParent.get('students').addObject(student);
        } else {
          //create new parent
          console.log("Creating new parents.");
          currentParent = store.createRecord('parent', {
            mother_name: mother,
            father_name: father
          });
          student.set('parent',currentParent);
          currentParent.get('students').addObject(student);
        }
      } catch (error) {
        console.log("Error querying: "+error);
      }

      //add advisor
      let rec2 = await store.query("advisor", {orderBy: "advisor_name", equalTo:advisor});
      //check if advisor exists
      try {
        if (rec2.get('firstObject') != undefined) {
          console.log("Advisor exists.");
          currentAdvisor = rec2.get('firstObject');
          student.set('advisor', currentAdvisor);
          currentAdvisor.get('students').addObject(student);
        } else {
          //create new advisor
          console.log("Creating new advisor.");
          currentAdvisor = store.createRecord('advisor', {
            advisor_name: advisor
          });
          student.set('advisor',currentAdvisor);
          currentAdvisor.get('students').addObject(student);
        }
      } catch (error) {
        console.log("Error querying: "+error);
      }

      //add classes
      for (let cla of clasz) {
        console.log('cla is '+cla);
        let rec3 = await store.query("class", {orderBy: "class_name", equalTo: cla});
        try {
          if (rec3.get('firstObject') != undefined) {
            let cClass = await store.findRecord('class', rec3.get('firstObject').get('id'));
             cClass.get('students').addObject(student);
             student.get('classes').pushObject(rec3.get('firstObject'));
             await cClass.save();
          } else {
            //create new class
            console.log("Creating new Class "+cla);
            let tClass = await store.createRecord('class', {
              class_name: cla,
            });
            tClass.get('students').addObject(student);
            student.get('classes').pushObject(rec3.get('firstObject'))
            await tClass.save();
          }
        } catch(error) {
          console.log("Error querying: "+error);
        }
      }
      //add books
      for (let book of books) {
        console.log('book is '+book);
        let rec4 = await store.query("book", {orderBy: "book_name", equalTo: book});
        try {
          if (rec4.get('firstObject') != undefined) {
            let cBook = await store.findRecord('book', rec4.get('firstObject').get('id'));
            cBook.set('student',student);
            student.get('books').pushObject(rec4.get('firstObject'));
            await cBook.save();
          } else {
            //create new book
            console.log("Creating new Book "+book);
            let tBook = await store.createRecord('book', {
              book_name: book,
            });
            await tBook.set('student',student).save();
            student.get('books').pushObject(rec4.get('firstObject'));
          }
        } catch(error) {
          console.log("Error querying: "+error);
        }
      }

      await currentDepartment.save();
      await currentAdvisor.save();
      await currentParent.save();
      await student.save();

      this.send('goToStudentList');
    }
  },
    goToStudentList() {
      this.transitionTo('main.student');
    }
  }
});
