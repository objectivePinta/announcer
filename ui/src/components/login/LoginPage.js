import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as loginActions from '../../actions/loginActions';
import {LoginFormObject} from '../../constants/LoginFormObject';
import IntelligentForm from '../common/IntelligentForm';
import toastr from 'toastr';

class LoginPage extends Component {

  constructor(props) {
    super(props);
    this.state = {
      username:'',
      password: ''
    };
    this.handle = this.handle.bind(this);
    this.onFieldsChange = this.onFieldsChange.bind(this);
  }

  componentWillMount() {

  }

  onSubmit() {
    console.log("click");
    this.props.loginActions.doLogin(this.state.username,this.state.password).then(response =>
    toastr.info(response.status));
  }

  handle(event) {
    console.log(event.target.value);
  }

  onFieldsChange(id, value) {
    console.log(id);
    console.log(value);
    if (id === 'name') {
     this.setState({username: value});
    } else {
     this.setState({password: value});
    }
    console.log('state',this.state);
  }


  render() {
    return (
      <div>
        <IntelligentForm
          title={LoginFormObject.title}
          onSubmit={() => {this.onSubmit()}}
          object={LoginFormObject.object}
          onFieldsChange={this.onFieldsChange}
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

