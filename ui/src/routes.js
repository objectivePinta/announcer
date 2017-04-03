import React from 'react';
// import URI from 'urijs';
import { Route, IndexRoute } from 'react-router';
// import underscore from 'underscore';
// import fetch from 'isomorphic-fetch';
import LoginPage from './components/login/LoginPage';
import App from './components/App';



export default function routes(store) {
  return (<Route path='/' component={App}>
      <IndexRoute component={LoginPage} />
      <Route path='login' component={LoginPage} />
      <Route path='*' component={LoginPage} />
    </Route>
  );
}
