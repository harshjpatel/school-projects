import Ember from 'ember';

export default Ember.Route.extend({
  authSvc: Ember.inject.service('authentication'),
  authorizedUser: false,
  authorizedAdmin: false,

  model() {
    var authSvc = this.get('authSvc');
    var username = authSvc.get('username');
    var password = authSvc.get('password');
    var authorizedUser = authSvc.get('authorizationUser');
    var authorizedAdmin = authSvc.get('authorizationAdmin');
    this.set('authorizedUser',authorizedUser);
    this.set('authorizedAdmin',authorizedAdmin);
    console.log('in main');
    console.log('user '+authorizedUser);
    console.log('admin '+authorizedAdmin);
    //TODO uncheck for prod
    if (!authorizedUser && !authorizedAdmin) {
     this.transitionTo('login');
    }
  },
  setupController(controller,model) {
    this._super(controller, model);
    if (this.get('authorizedUser') || this.get('authorizedAdmin')) {
      this.controllerFor('application').set('showMenu',true);
    }
    if (this.get('authorizedUser')) {
      this.controllerFor('main').set('showVMenuUser',true);
    } else if (this.get('authorizedAdmin')){
      this.controllerFor('main').set('showVMenuAdmin',true);
    }
  },
  actions: {
    goToRoute(route) {
      this.transitionTo("main."+route);
    }
  }
});
