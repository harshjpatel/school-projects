import Ember from 'ember';

export default Ember.Route.extend({
  model(params) {
    return this.get('store').findRecord('class', params.id);
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isClass', true);
  },
  actions: {
    async saveClass(name,dept,teacher,students) {
      var currentDepartment
      var currentStudent;
      var currentTeacher;
      var currentClass;
      var store = this.get('store');
      let model = this.modelFor(this.routeName);

      if (model == null) { //create new classes
        let newClass = await this.get('store').createRecord('class', {
          class_name: name,
        }).save()

        currentClass = await store.peekRecord('class', newClass.get('id'));

        let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
        try {
          //check if department exists
          if (rec.get('firstObject') != undefined) {
            console.log("Department exists.");
            currentDepartment = rec.get('firstObject');
            currentClass.set('department', currentDepartment);
            currentDepartment.get('classes').addObject(currentClass);
          } else {
            //create new department
            console.log("Creating new department.");
            currentDepartment = store.createRecord('department', {
              dept_name: dept
            });
            currentClass.set('department', currentDepartment);
            currentDepartment.get('classes').addObject(currentClass);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }

        let rec1 = await store.query("teacher",{orderBy: "teacher_name", equalTo: teacher});
        try {
          //check if teacher exists
          if (rec1.get('firstObject') != undefined) {
            console.log("Teacher exists.");
            currentTeacher = rec.get('firstObject');
            currentClass.set('teacher', currentTeacher);
            currentTeacher.get('classes').addObject(currentClass);
          } else {
            //create new teacher
            console.log("Creating new teacher.");
            currentTeacher = store.createRecord('teacher', {
              teacher_name: teacher
            });
            currentClass.set('teacher', currentTeacher);
            currentTeacher.get('classes').addObject(currentClass);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }

        //add students
        for (let student of students) {
          let rec2 = await store.query("student", {orderBy: "student_name", equalTo: student});
          try {
            if (rec2.get('firstObject') != undefined) {
              let cStudent = await store.findRecord('student', rec2.get('firstObject').get('id'));
              await cStudent.get('classes').addObject(currentClass).save();
              await currentClass.get('students').pushObject(rec2.get('firstObject')).save();
            } else {
              //create new student
              console.log("Creating new Student "+student);
              let tStudent = await store.createRecord('student', {
                student_name: student,
              }).save();
              await tStudent.get('classes').addObject(currentClass);
              await currentClass.get('students').pushObject(rec2.get('firstObject')).save();
            }
          } catch(error) {
            console.log("Error querying: "+error);
          }
        }
        await currentDepartment.save();
        await currentTeacher.save();
        await currentClass.save();

        this.send('goToClassList');
    } else { //class already exists, update class
        let clasz = await this.get('store').findRecord('class',model.id);

        clasz.set('class_name',name);

        let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
        try {
          //check if department exists
          if (rec.get('firstObject') != undefined) {
            console.log("Department exists.");
            currentDepartment = rec.get('firstObject');
            clasz.set('department', currentDepartment);
            currentDepartment.get('classes').addObject(clasz);
          } else {
            //create new department
            console.log("Creating new department.");
            currentDepartment = store.createRecord('department', {
              dept_name: dept
            });
            clasz.set('department', currentDepartment);
            currentDepartment.get('classes').addObject(clasz);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }

        let rec1 = await store.query("teacher",{orderBy: "teacher_name", equalTo: teacher});
        try {
          //check if teacher exists
          if (rec1.get('firstObject') != undefined) {
            console.log("Teacher exists.");
            currentTeacher = rec.get('firstObject');
            clasz.set('teacher', currentTeacher);
            currentTeacher.get('classes').addObject(clasz);
          } else {
            //create new teacher
            console.log("Creating new teacher.");
            currentTeacher = store.createRecord('teacher', {
              teacher_name: teacher
            });
            clasz.set('teacher', currentTeacher);
            currentTeacher.get('classes').addObject(clasz);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }

        //add students
        for (let student of students) {
          let rec2 = await store.query("student", {orderBy: "student_name", equalTo: student});
          try {
            if (rec2.get('firstObject') != undefined) {
              let cStudent = await store.findRecord('student', rec2.get('firstObject').get('id'));
              await cStudent.get('classes').addObject(clasz).save();
              await clasz.get('students').pushObject(rec2.get('firstObject')).save();
            } else {
              //create new student
              console.log("Creating new Student "+student);
              let tStudent = await store.createRecord('student', {
                student_name: student,
              }).save();
              await tStudent.get('classes').addObject(clasz);
              await clasz.get('students').pushObject(rec2.get('firstObject')).save();
            }
          } catch(error) {
            console.log("Error querying: "+error);
          }
        }
        await currentDepartment.save();
        await currentTeacher.save();
        await clasz.save();

      this.send('goToClassList');
    }
  },
    goToClassList() {
      this.transitionTo('main.class');
    }
  }
});
