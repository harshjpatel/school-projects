import Ember from 'ember';
import config from './config/environment';

const Router = Ember.Router.extend({
  location: config.locationType,
  rootURL: config.rootURL
});

Router.map(function() {
  this.route('login');
  this.route('main', function() {
    this.route('admin');
    this.route('advisor');
    this.route('book');
    this.route('class');
    this.route('department');
    this.route('parent');
    this.route('student');
    this.route('teacher')
    this.route('user');
    this.route('booksco');
    this.route('eadmin', {path: '/eadmin/:id'});
    this.route('ebook', {path: '/ebook/:id'});
    this.route('eteacher',{path: '/eteacher/:id'});
    this.route('eadvisor', {path: '/eadvisor/:id'});
    this.route('estudent', {path: '/estudent/:id'});
    this.route('edepartment', {path: '/edepartment/:id'});
    this.route('eclass', {path: '/eclass/:id'});
    this.route('euser', {path: '/euser/:id'});
    this.route('ebookco', {path: '/ebookco/:id'});
    this.route('overview');
  });
  this.route('error');
});

export default Router;
