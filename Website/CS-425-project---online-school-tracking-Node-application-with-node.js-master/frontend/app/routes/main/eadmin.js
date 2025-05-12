import Ember from 'ember';

export default Ember.Route.extend({
  model(params) {
    return this.get('store').findRecord('admin', params.id);
  },
  setupController: function(controller, model) {
    //this takes the route's model and allows the template to gain access
      this._super(controller, model);
      controller.set('model', model);
      controller.set('isAdmin', true);
  },
  actions: {
    saveAdmin(name,email) {
      let model = this.modelFor(this.routeName);
      if (model == null) { //create new admin
        let admin = this.get('store').createRecord('admin', {
          admin_name: name,
          admin_email: email
        });
        admin.save();
        this.send('goToAdminList');
    } else { //admin already exists, update admin
        this.get('store').findRecord('admin',model.id).then(function(admin) {
          admin.set('admin_name',name);
          admin.set('admin_email',email);
          admin.save();
      });
      this.send('goToAdminList');
    }
  },
    goToAdminList() {
      this.transitionTo('main.admin');
    }
  }
});
