import Ember from 'ember';

export default Ember.Component.extend({
  didRender() {
    //initially hide the sidebar
    Ember.$('.sidebar').hide();
  },

  actions: {
      expandMenu() {
        console.log("menu clicked");
        let hamburger = Ember.$('.hamburger');
        let sidebar = Ember.$('.sidebar');

        //expand or hide the sidebar
        sidebar.animate({width:'toggle'},'fast');

        //keep hamburger green
        if (sidebar.width() < 1) {
          hamburger.css({fill:'#2AD267'});
        } else {
          hamburger.css({fill:'#8393a7'});
        }
      },
      logOut() {
        console.log("power clicked");
        //logout of application -> sends to login screen
        this.sendAction('logOut');
      }
  }
});
