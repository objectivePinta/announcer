import URI from 'urijs';
import toastr from 'toastr';
import * as constants from '../constants/constants';
import apiFetch from './apiFetch';


export function doLogin(username, password) {
  console.log(username,password);
  return function (dispatch) {
    const requestUri = new URI(constants.ROOT).segment('login');
    const data = new URI().query({ username, password }).query();
    return apiFetch(dispatch, requestUri, {
      credentials: 'include',
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
      },
      body: data,
    }).then(response => {
     // dispatch(logoutActions.resetState());
      if (response.status === 200) {
        if (username !== 'dm') {
          toastr.info('Login successful.');
        }
       // dispatch(setLoggedInUser(username));
        return response;
      } else if (response.status === 500) {
        throw Error('Invalid username/password');
      }
      throw Error('Login failed, please try again.');
    });
  };
}
