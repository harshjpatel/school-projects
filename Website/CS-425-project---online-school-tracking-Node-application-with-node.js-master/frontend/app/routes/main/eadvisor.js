import Ember from 'ember';

export default Ember.Route.extend({
  model(params) {
    return this.get('store').findRecord('advisor', params.id);
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isAdvisor', true);
  },
  actions: {
    async saveAdvisor(name,email,dept) {
      var currentAdvisor;
      var currentDepartment;
      var store = this.get('store');

      let model = this.modelFor(this.routeName);
      if (model == null) { //create new advisor
        let newAdvisor = await this.get('store').createRecord('advisor', {
          advisor_name: name,
          advisor_email: email,
        }).save()
        currentAdvisor = await store.peekRecord('advisor', newAdvisor.get('id'));

        let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
        try {
          //check if department exists
          if (rec.get('firstObject') != undefined) {
            console.log("Department exists.");
            currentDepartment = rec.get('firstObject');
            currentAdvisor.set('department', currentDepartment);
            currentDepartment.get('advisors').addObject(currentAdvisor);
          } else {
            //create new department
            console.log("Creating new department.");
            currentDepartment = store.createRecord('department', {
              dept_name: dept
            });
            currentAdvisor.set('department', currentDepartment);
            currentDepartment.get('advisors').addObject(currentAdvisor);
          }
        } catch (error) {
          console.log("Error querying: "+error);
        }
        await currentDepartment.save();
        await currentAdvisor.save();

        this.send('goToAdvisorList');
    } else { //advisor already exists, update advisor
      let advisor = await this.get('store').findRecord('advisor',model.id);
      advisor.set('advisor_name',name);

      let rec = await store.query("department",{orderBy: "dept_name", equalTo: dept});
      try {
        //check if department exists
        if (rec.get('firstObject') != undefined) {
          console.log("Department exists.");
          currentDepartment = rec.get('firstObject');
          advisor.set('department', currentDepartment);
          currentDepartment.get('advisors').addObject(advisor);
        } else {
          //create new department
          console.log("Creating new department.");
          currentDepartment = store.createRecord('department', {
            dept_name: dept
          });
          advisor.set('department', currentDepartment);
          currentDepartment.get('advisors').addObject(advisor);
        }
      } catch (error) {
        console.log("Error querying: "+error);
      }

      await currentDepartment.save();
      await advisor.save();

      this.send('goToAdvisorList');
    }
  },
    goToAdvisorList() {
      this.transitionTo('main.advisor');
    }
  }
});
