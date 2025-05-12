import Ember from 'ember';

export default Ember.Route.extend({
  model(params) {
    return this.get('store').findRecord('user', params.id);
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isUser', true);
  },
  actions: {
    async saveUser(name,email, dept) {
      var currentUser;
      var currentDepartment;
      var store = this.get('store');

      let model = this.modelFor(this.routeName);
      if (model == null) { //create new user
        let newUser = await this.get('store').createRecord('user', {
          user_name: name,
          user_email: email
        }).save();

        let currentUser = await store.peekRecord('user', newUser.get('id'));

        let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
        try {
          //check if department exists
          if (rec.get('firstObject') != undefined) {
            console.log("Department exists.");
            currentDepartment = rec.get('firstObject');
            currentUser.set('department', currentDepartment);
            currentDepartment.get('users').addObject(currentUser);
          } else {
            //create new department
            console.log("Creating new department.");
            currentDepartment = store.createRecord('department', {
              dept_name: dept
            });
            currentUser.set('department', currentDepartment);
            currentDepartment.get('users').addObject(currentUser);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }
        await currentDepartment.save();
        await currentUser.save();

        this.send('goToUserList');
    } else { //Users already exists, update user
      let user = await this.get('store').findRecord('user',model.id);
      user.set('user_name',name);

      let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
      try {
        //check if department exists
        if (rec.get('firstObject') != undefined) {
          console.log("Department exists.");
          currentDepartment = rec.get('firstObject');
          user.set('department', currentDepartment);
          currentDepartment.get('users').addObject(user);
        } else {
          //create new department
          console.log("Creating new department.");
          currentDepartment = store.createRecord('department', {
            dept_name: dept
          });
          user.set('department', currentDepartment);
          currentDepartment.get('users').addObject(user);
        }
      } catch (error) {
        console.log("Error querying: "+error);
      }

      await currentDepartment.save();
      await user.save();
      this.send('goToUserList');
    }
  },
    goToUserList() {
      this.transitionTo('main.user');
    }
  }
});
