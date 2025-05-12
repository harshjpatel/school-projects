import Ember from 'ember';

export default Ember.Component.extend({
  lastLoginUsername: "Chris",
  username: null,
  password: null,
  authorizationUser: false,
  authorizationAdmin: false,
  store: Ember.inject.service(),

  actions: {
    async submit() {
      let store = this.get('store');
      let username = this.get('username');
      let password = this.get('password');
      let currentUser = await store.query("user", {orderBy: "user_name", equalTo: username});
      if (username && password) {
        if (username == "admin" && password == "password") {
          this.set('authorizationAdmin',true);
          var obj = Ember.Object.create({
            username: username,
            password: password,
            authorizationUser: this.get("authorizationUser"),
            authorizationAdmin: this.get('authorizationAdmin')
          });
          this.sendAction('action',obj);
        }
        else {
          try {
            if (currentUser.get('firstObject') != undefined) {
              let user = await store.findRecord('user', currentUser.get('firstObject').get('id'));
              if (username && password) {
                this.set('authorizationUser', true);
                var obj = Ember.Object.create({
                  username: user.get('user_name'),
                  password: password,
                  authorizationUser: this.get("authorizationUser"),
                  authorizationAdmin: this.get('authorizationAdmin')
                })
              }
              console.log('user found is '+user.get('user_name'));
              this.sendAction('action',obj);
            } else {
              console.log('no user found matching.')
            }
          } catch(error) {
            console.log("Error querying: "+error);
          }
          // console.log("Wrong User/Pass");
        }
      } else {
        console.log("Nothing Entered");
      }
    }
  }
});
