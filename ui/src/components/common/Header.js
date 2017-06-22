import React, {Component, PropTypes} from 'react';
import URI from 'urijs';
import {Link} from 'react-router';

class Header extends Component {

  constructor(context,props) {
    super(context, props);
  }


  render() {
    return(
      <div>
        <ul>
          <Link to={`/register`} activeClassName="active">Register</Link>
        </ul>
      </div>
    );
  }
}

Header.contextTypes = {
  router: PropTypes.object.isRequired,
};


export default Header;
