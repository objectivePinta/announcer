import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as loginActions from '../../actions/loginActions';
import * as logoutActions from '../../actions/logoutActions';
import URI from 'urijs';
import {LoginFormObject} from '../../constants/LoginFormObject';
import bg1 from '../../images/concert.jpeg';
import IntelligentForm from '../common/IntelligentForm';
import toastr from 'toastr';
import * as styles from '../styles/login/loginPage.dino.css';

const background = bg1;

class LoginPage extends Component {

  constructor(context,props) {
    super(context, props);
    this.state = {
      username: '',
      password: ''
    };
    this.handle = this.handle.bind(this);
    this.onFieldsChange = this.onFieldsChange.bind(this);
  }

  onSubmit() {
    this.props.loginActions.doLogin(this.state.username, this.state.password).then(response => {
      if (response.status === 200) {
        toastr.info("Login was successfull ;)");
        this.context.router.push({ pathname: new URI('/dummy').toString()});
      } else {
        toastr.info("Login failed ;)");
      }
    });
  }

  handle(event) {
    this.props.logoutActions.doLogout()
  }

  onFieldsChange(id, value) {
    if (id === 'name') {
      this.setState({username: value});
    } else {
      this.setState({password: value});
    }
  }


  render() {
    return (
      <div className={styles.loginPage}>
        <h1>Log in</h1>
        <IntelligentForm
          title={LoginFormObject.title}
          onSubmit={() => {this.onSubmit()}}
          object={LoginFormObject.object}
          onFieldsChange={this.onFieldsChange}
        />
          <button onClick={this.handle}>Log out</button>
      </div>
    );
  }

}

LoginPage.contextTypes = {
  router: PropTypes.object,
};

LoginPage.propTypes = {
  loginActions: PropTypes.object,
  logoutActions: PropTypes.object,
};

function mapStateToProps(state, ownProps) {
  return {};
}

function mapDispatchToProps(dispatch) {
  return {
    loginActions: bindActionCreators(loginActions, dispatch),
    logoutActions: bindActionCreators(logoutActions, dispatch),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(LoginPage);

