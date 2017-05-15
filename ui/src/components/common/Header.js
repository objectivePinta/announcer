import React, {Component, Proptypes} from 'react';

class Header extends Component {

  constructor(context,props) {
    super(context, props);
  }

  render() {
    return(
      <div>
        <ul>
          <li>acacia</li>
          <li>acadia</li>
          <li>adana</li>
          <li>aluna</li>
          <li>acadia</li>
        </ul>
      </div>
    );
  }
}

export default Header;
