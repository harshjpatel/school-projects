import Ember from 'ember';

export default Ember.Route.extend({
  model(params) {
    return this.get('store').findRecord('department', params.id);
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isDepartment', true);
  },
  actions: {
    saveDepartment(name) {
      let model = this.modelFor(this.routeName);
      if (model == null) { //create new department
        let department = this.get('store').createRecord('department', {
          dept_name: name,
        });
        department.save();
        this.send('goToDepartmentList');
    } else { //department already exists, update department
        this.get('store').findRecord('department',model.id).then(function(department) {
          department.set('department_name',name);
          department.save();
      });
      this.send('goToDepartmentList');
    }
  },
    goToDepartmentList() {
      this.transitionTo('main.department');
    }
  }
});
