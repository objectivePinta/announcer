import URI from 'urijs';
import * as constants from '../constants/constants';
import apiFetch from './apiFetch';


export function doLogin(username, password) {
    return function (dispatch) {
        const requestUri = new URI(constants.ROOT).segment('login');
        const data = new URI().query({username, password}).query();
        return apiFetch(dispatch, requestUri, {
            credentials: 'include',
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
            },
            body: data,
        }).then(response => {
            if (response.status === 200) {
                return response;
            } else if (response.status === 401) {
              return response;

            }
        });
    };
}

export function registerUser(username, password) {
    return function (dispatch) {
        const requestUri = new URI(constants.API_ROOT).segment('registration');
        const data = {username, password};
        return apiFetch(dispatch, requestUri, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        }).then(response => {
            if (response.status === 200) {
                // dispatch(setLoggedInUser(username));
                return response;
            } else if (response.status === 500) {
                throw Error('Invalid username/password');
            }
            // throw Error('Login failed, please try again.');
        });
    };
}
