import Ember from 'ember';

export default Ember.Component.extend({
  showAdminMenu: Ember.computed(function(){
    return false;
  }),
  didRender() {
    var This = this;
    //retrieve elements
    var overview = Ember.$('#overview');
    var users = Ember.$('#users');
    var books = Ember.$('#books');
    var booksco = Ember.$('#booksco');
    var students = Ember.$('#students');
    var teachers = Ember.$('#teachers');
    var advisors = Ember.$('#advisors');
    var departments = Ember.$('#departments');
    var classes = Ember.$('#classes');
    var parents = Ember.$('#parents');
    var admins = Ember.$('#admins');
    var showUserMenu = this.get('showUserMenu');
    var showAdminMenu = this.get('showAdminMenu');
    var entityArray;
    //create array
    if (this.get('showAdminMenu') == false) { //user
      entityArray = [overview,books,booksco,students,teachers,advisors,departments,classes,parents];
    } else if (this.get('showAdminMenu') == true){ //admin
      entityArray = [overview,users,books,booksco,students,teachers,advisors,departments,classes,parents,admins];
    }

    for (var i=0; i<entityArray.length; i++) {
      //console.log(entityArray[i].text()); //shows all text in array
      let $this = entityArray[i];
      $this.click(function() {
        $this.closest('li').addClass('highlight').siblings().removeClass('highlight');
        //console.log($this.text().toLowerCase()); //shows clicked text
        //go to route that is clicked on
        if ($this.text() == "Books C/O") {
          This.send('goToRoute','booksco');
        } else {
          This.send('goToRoute',$this.attr('aria-label').toLowerCase());
        }
      });
    }
  },
  actions: {
      goToRoute(route) {
        this.sendAction('goToRoute', route);
      }
  }
});
