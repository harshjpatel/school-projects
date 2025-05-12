import Ember from 'ember';

export default Ember.Route.extend({
  authSvc: Ember.inject.service('authentication'),

  actions: {
    logOutClient() {
      let authSvc = this.get('authSvc');
      authSvc.set('authorizationUser',false);
      authSvc.set('authorizationAdmin',false);
      authSvc.set('username', null);
      authSvc.set('password',null);
      this.controllerFor('main').set('showVMenuAdmin',false);
      this.refresh();
      this.transitionTo('login');
    }
  }
});
