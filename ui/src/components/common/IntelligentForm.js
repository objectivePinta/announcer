import React, {Component, PropTypes} from 'react';
import toastr from 'toastr';
import TextInput from './form-components/TextInput';
import PrintObject from '../utils/PrintObject';

class IntelligentForm extends Component {

  constructor(props) {
    super(props);
    let objectOfForm = {};
    let fieldsValidity = {};
    Object.keys(this.props.object).map( key => {
      objectOfForm[key] = '';
      fieldsValidity[key] = false;
     return {objectOfForm,fieldsValidity};
    });
    this.state = {objectOfForm,
                  fieldsValidity,
                   valid: false };
    this.handleTextChange = this.handleTextChange.bind(this);
    this.buttonClick = this.buttonClick.bind(this);
    this.areAllValid = this.areAllValid.bind(this);
  }

  handleTextChange(event) {
    let copyOfObjectOfForm = Object.assign({},this.state.objectOfForm);
    copyOfObjectOfForm[event.target.id] = event.target.value;
    this.setState({objectOfForm:copyOfObjectOfForm});
    this.props.onFieldsChange(event.target.id, event.target.value);
  }

  buttonClick(event) {
    event.preventDefault();
    let keys = Object.keys(this.state.fieldsValidity);
    let trues = keys.map(t =>
    this.state.fieldsValidity[t]).filter(t => t === true);
    if (trues.length === keys.length ) {
      this.props.onSubmit(Object.assign({}, this.state.objectOfForm));
      toastr.info('Data was submitted succesfully :)');

    } else {
      toastr.warning('You still have to fill some stuff around here :)');
    }
  }

  areAllValid(id , valid) {
    this.state.fieldsValidity[id] = valid;
  }

  render() {
    const props = Object.keys(this.state.objectOfForm);
    const inputs = props.map(
      prop =>
      {
          return (<TextInput
            key={prop}
            type={this.props.object[prop].type}
            id={prop}
            inputValue={this.state.objectOfForm[prop]}
            textChanged={this.handleTextChange}
            minSize={this.props.object[prop].minSize}
            allowNumbers={this.props.object[prop].allowNumbers}
            validate={this.areAllValid}
          /> );
      }
    );

    return (
      <ul className="form-style-1" style={{border:'0.5px dashed grey', padding:'13px'}}>
        <h3>{this.props.title} </h3>
          {inputs}
         <button onClick={this.buttonClick} className="btn btn-info btn-submit"> Boom </button>
        {this.props.debug && <PrintObject givenObject={this.state.objectOfForm}/>}
      </ul>
    );
  }

}

IntelligentForm.propTypes = {
  object: PropTypes.object,
  onSubmit: PropTypes.func,
  title: PropTypes.string,
  debug: PropTypes.bool
};

export default IntelligentForm;
