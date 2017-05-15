import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';
import {bindActionCreators} from 'redux';
import * as loginActions from '../../actions/loginActions';
import {RegistrationFormObject} from '../../constants/RegistrationFormObject';
import IntelligentForm from '../common/IntelligentForm';
import toastr from 'toastr';

class RegistrationPage extends Component {

  constructor(props) {
    super(props);
    this.state = {
      username:'',
      password: ''
    };
    this.handle = this.handle.bind(this);
    this.onFieldsChange = this.onFieldsChange.bind(this);
  }


  onSubmit() {
    this.props.loginActions.registerUser(this.state.username,this.state.password).then(response =>
    toastr.info(response.status));
  }

  handle(event) {
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
      <div>
        <IntelligentForm
          title={RegistrationFormObject.title}
          onSubmit={() => {this.onSubmit()}}
          object={RegistrationFormObject.object}
          onFieldsChange={this.onFieldsChange}
        />
      </div>
    );
  }

}

RegistrationPage.propTypes = {
  loginActions: PropTypes.object,
};

function mapStateToProps(state, ownProps) {
  return {};
}

function mapDispatchToProps(dispatch) {
  return {
    loginActions: bindActionCreators(loginActions, dispatch),
  };
}

export default connect(mapStateToProps, mapDispatchToProps)(RegistrationPage);

