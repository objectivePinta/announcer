import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as loginActions from '../../actions/loginActions';
import {LoginFormObject} from '../../constants/LoginFormObject';
import IntelligentForm from '../common/IntelligentForm';

class LoginPage extends Component {

  constructor(props) {
    super(props);
    this.handle = this.handle.bind(this);
  }

  componentWillMount() {
    this.props.loginActions.doLogin('andrei1', 'andrei1');

  }

  onSubmit(wow) {
    console.log(wow);
    setTimeout(() => {this.props.loginActions.doLogin('a','b')},100);
  }

  handle(event) {
    console.log(event.target.value);
  }


  render() {
    return (
      <div>
        <IntelligentForm
          title={LoginFormObject.title}
          onSubmit={this.onSubmit}
          object={LoginFormObject.object}
        />
        <div class="container">
          Login with:
          <button onClick={this.handle}>Facebook</button>
        </div>
      </div>
    );
  }

}

function mapStateToProps(state, ownProps) {
  return {};
}

function mapDispatchToProps(dispatch) {
  return {
    loginActions: bindActionCreators(loginActions, dispatch),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);

