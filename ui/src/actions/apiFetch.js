import URI from 'urijs';
import fetch from 'isomorphic-fetch';
import { push } from 'react-router-redux';
import * as constants from '../constants/constants';
// import * as ajaxActions from './ajaxActions';
//import * as logoutActions from './logoutActions';

export default function apiFetch(dispatch, resource, request) {
  console.log('fetching');
//  const startTime = dispatch(ajaxActions.ajaxStarted());
  return fetch(resource.toString(), request)
    .then(response => {
    //  dispatch(ajaxActions.ajaxFinished(startTime));
      if (response.status === 204) {
     //   dispatch(logoutActions.resetState());
          console.log('fetch2ing');

          dispatch(push(new URI(constants.ROOT).segment('login').toString()));
        if (resource.equals(new URI(constants.ROOT).segment('login'))) {
            console.log('fetch3ing');

            throw new Error('Login failed, please try again.');

        }
        throw new Error('Logged out, please log in again.');
          console.log('fetch4ing');

      }
      return response;
    });
}
