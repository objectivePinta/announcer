/* eslint-disable import/default */
import URI from 'urijs';
import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { syncHistoryWithStore } from 'react-router-redux';
import { Router, useRouterHistory } from 'react-router';
import { createHistory } from 'history';
 import { IntlProvider, addLocaleData } from 'react-intl';
import configureStore from './store/configureStore';
import DevTools from './components/DevTools';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
// import '../node_modules/react-bootstrap-toggle/dist/bootstrap2-toggle.css';
import '../node_modules/toastr/build/toastr.min.css';
// import '../node_modules/react-bootstrap-table/dist/react-bootstrap-table-all.min.css';
// import '../node_modules/dropzone/dist/min/dropzone.min.css';
// import '../node_modules/react-datepicker/dist/react-datepicker.css';
// import './styles/old/metro.css';
// import './styles/main.scss';
import * as constants from './constants/constants';
import routes from './routes';
import Header from './components/common/Header';

// if (window.ReactIntlLocaleData) {
//   addLocaleData(window.ReactIntlLocaleData[window.locale]);
// }
const browserHistory = useRouterHistory(createHistory)({
  basename: URI.parse(constants.ROOT).path
});

const store = configureStore(browserHistory);
const history = syncHistoryWithStore(browserHistory, store);

render(
  <Provider store={store}>
    <IntlProvider locale={window.locale}>
      <div style={{ height: '100%' }}>
        <Header/>
        <Router history={history} routes={routes(store)} />
        {false && <DevTools />}
      </div>
    </IntlProvider>
  </Provider>,
  document.getElementById('app')
);

/* eslint-enable import/default */
