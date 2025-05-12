import Ember from 'ember';

export default Ember.Service.extend({
  username: null,
  password: null,
  authorizationUser: false,
  authorizationAdmin: false
});
