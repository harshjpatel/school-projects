import Ember from 'ember';

export default Ember.Route.extend({
  store: Ember.inject.service(),

  model(params) {
    return this.get('store').findRecord('teacher', params.id);
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isTeacher', true);
      console.log('model is '+this.modelFor(this.routeName));
  },
  actions: {
    async saveTeacher(name,email,clasz,dept) {
      var store = this.get('store');
      var currentDepartment
      var currentTeacher;
      let model = this.modelFor(this.routeName);
      var $this = this;

      //check if department,classes, and email exist
      if (model == null) { //no ID given so adding new teacher
        //create teacher record
        let newTeacher = await store.createRecord('teacher',{
          teacher_name: name,
          teacher_email: email
        }).save();

        currentTeacher = await store.peekRecord('teacher', newTeacher.get('id'));

        let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
        try {
          //check if department exists
          if (rec.get('firstObject') != undefined) {
            console.log("Department exists.")
            currentDepartment = rec.get('firstObject');
            currentTeacher.set('department',currentDepartment);
            currentDepartment.get('teachers').addObject(currentTeacher);

          } else {
            //create new department
            console.log("Creating new department.");
            currentDepartment = store.createRecord('department', {
              dept_name: dept
            });
            currentTeacher.set('department',currentDepartment);
            currentDepartment.get('teachers').addObject(currentTeacher);
          }
        } catch(error) {
          console.log("Error querying");
        }

        for (let cla of clasz) {
          let rec1 = await store.query("class", {orderBy: "class_name", equalTo: cla});
          try {
            if (rec1.get('firstObject') != undefined) {
              let cClass = await store.findRecord('class', rec1.get('firstObject').get('id'));
              cClass.set('teacher',currentTeacher);
              currentTeacher.get('classes').pushObject(rec1.get('firstObject'));
              cClass.save();
            } else {
              //create new class
              console.log("Creating new Class "+cla);
              let tClass = store.createRecord('class', {
                class_name: cla,
              });
              tClass.set('teacher',currentTeacher);
              await tClass.save();
              currentTeacher.get('classes').pushObject(rec1.get('firstObject'));
            }
          } catch(error) {
            console.log("Error querying: "+error);
          }
        }
        //final save
        await currentDepartment.save();
        await currentTeacher.save();

        console.log('transition');
        $this.send('goToTeacherList');
      }
      else { //model not null - PATCH
        let teacher = await this.get('store').findRecord('teacher',model.id);
        teacher.set('teacher_name',name);
        teacher.set('teacher_email',email);

        let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
        try {
          //check if department exists
          if (rec.get('firstObject') != undefined) {
            console.log("Department exists.")
            currentDepartment = rec.get('firstObject');
            teacher.set('department',currentDepartment);
            currentDepartment.get('teachers').addObject(teacher);
          } else {
            //create new department
            console.log("Creating new department.");
            currentDepartment = store.createRecord('department', {
              dept_name: dept,
            });
            teacher.set('department',currentDepartment);
            currentDepartment.get('teachers').addObject(teacher);
          }
        } catch(error) {
          console.log("Error querying");
        }

        //remove all classes
        await teacher.get('classes').clear();
        for (let cla of clasz) {
          let rec1 = await store.query("class", {orderBy: "class_name", equalTo: cla});
          try {
            if (rec1.get('firstObject') != undefined) {
              let cClass = await store.findRecord('class', rec1.get('firstObject').get('id'));
              cClass.set('teacher',teacher);
              teacher.get('classes').pushObject(rec1.get('firstObject'));
              cClass.save();
            } else {
              //create new class
              console.log("Creating new Class "+cla);
              let tClass = store.createRecord('class', {
                class_name: cla,
              });
              tClass.set('teacher',teacher);
              await tClass.save();
              teacher.get('classes').pushObject(rec1.get('firstObject'));
            }
      } catch(error) {
        console.log("Error querying: "+error);
        }
      }
      //final save
      await currentDepartment.save();
      await teacher.save();

      console.log('transition');
      $this.send('goToTeacherList');
    }
  },
    goToTeacherList() {
      this.transitionTo('main.teacher');
    },
  }
});
