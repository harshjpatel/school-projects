import Ember from 'ember';

export default Ember.Route.extend({
  authSvc: Ember.inject.service('authentication'),
  actions: {
    loginSuccess(success) {
      var authSvc = this.get('authSvc');
      authSvc.set('username',success.username);
      authSvc.set('password',success.password);
      authSvc.set('authorizationUser',success.authorizationUser);
      authSvc.set('authorizationAdmin',success.authorizationAdmin);
      this.transitionTo('main');
    }
  }
});
