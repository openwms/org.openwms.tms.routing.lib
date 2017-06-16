import React from 'react';

import PropTypes from 'prop-types';

class EditForm extends React.Component {

    constructor() {
        super()

        this.state = {
            obj: {},
        }
    }

    render() {
        return (
            <div className='row'>
                <div className='row'>
                    <div className="container">
                        <div className="row">
                            <h1>
                            <div className="col-md-10">Create Route</div>
                            <div className="col-md-2">
                                <button className="btn btn-default" onClick={this.props.onBack}><span className="glyphicon glyphicon-trash"></span></button>
                                <button className="btn btn-default" onClick={this.props.onSave}><span className="glyphicon glyphicon-ok"></span></button>
                            </div>
                            </h1>
                        </div>
                    </div>                
                </div>
                <hr />
                <form className="form-horizontal">
                    <div className="container">
                        <div className="form-group">
                            <label htmlFor="routeId-txt" className="col-xs-2 col-form-label">Route ID</label>
                            <div className="col-xs-10">
                                <input className="form-control" type="text" id="routeId-txt" />
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="description-txt" className="col-xs-2 col-form-label">Description</label>
                            <div className="col-xs-10">
                                <textarea className="form-control" id="description-txt" rows="3"/>
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="sourceLocation-txt" className="col-xs-2 col-form-label">Source Location</label>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="sourceLocationArea-txt"/>
                            </div>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="sourceLocationAisle-txt"/>
                            </div>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="sourceLocationX-txt"/>
                            </div>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="sourceLocationX-txt"/>
                            </div>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="sourceLocationZ-txt"/>
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="sourceLocationGroup-txt" className="col-xs-2 col-form-label">Source Location Group</label>
                            <div className="col-xs-10">
                                <select className="form-control" id="sourceLocationGroup-txt">
                                    <option>ZILE</option>
                                    <option>FGTIPP</option>
                                    <option>FGPALETT</option>
                                    <option>FGCARTON</option>
                                    <option>FGAISLE1</option>
                                </select>                        
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="targetLocation-txt" className="col-xs-2 col-form-label">Target Location</label>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="targetLocationArea-txt"/>
                            </div>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="targetLocationAisle-txt"/>
                            </div>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="targetLocationX-txt"/>
                            </div>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="targetLocationX-txt"/>
                            </div>
                            <div className="col-xs-2">
                                <input className="form-control" type="text" id="targetLocationZ-txt"/>
                            </div>
                        </div>

                        <div className="form-group">
                            <label htmlFor="targetLocationGroup-txt" className="col-xs-2 col-form-label">Target Location Group</label>
                            <div className="col-xs-10">
                                <select className="form-control" id="targetLocationGroup-txt">
                                    <option>ZILE</option>
                                    <option>FGTIPP</option>
                                    <option>FGPALETT</option>
                                    <option>FGCARTON</option>
                                    <option>FGAISLE1</option>
                                </select>                        
                            </div>
                        </div>

                        <div className="form-group">
                            <label className="col-xs-2 col-form-label"></label>
                            <div className="col-xs-10">
                                <div className="form-check">
                                    <button className={this.props.value.enable ? 'btn btn-success' : 'btn btn-danger'} type="button">{this.props.value.enable ? 'Enabled' : 'Disabled'}</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}

EditForm.propTypes = {
  value: PropTypes.object.isRequired,
  onBack: PropTypes.func.isRequired,
  onSave: PropTypes.func.isRequired,
}

export default EditForm;