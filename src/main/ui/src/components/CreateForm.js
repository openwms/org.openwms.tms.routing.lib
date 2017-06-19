import React from 'react';

import PropTypes from 'prop-types';

import Coordinate from './Coordinate';
import LocationGroupSelector from './LocationGroupSelector';

class CreateForm extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
                route : {
                name: '',
                description: '',
                sourceLocationGroupName: '',
                targetLocationGroupName: '',
                enabled: true,
                sourceLocation: '',
                targetLocation: '',
            }
        }
    }

    handleSave(event) {
        this.props.onSave(this.state.route);
    }

    handleCancel(event) {
        this.props.onBack();
    }

    handleChangeName(event) {
        this.setState(Object.assign(this.state.route,{name: event.target.value}))
    }

    handleChangeDescription(event) {
        this.setState(Object.assign(this.state.route,{description: event.target.value}))
    }

    handleChangeSourceLocation(coord, event) {
        this.setState(Object.assign(this.state.route,{sourceLocation: coord}))
    }

    handleChangeSourceLocationGroupName(locationGroupName, event) {
        this.setState(Object.assign(this.state.route,{sourceLocationGroupName: locationGroupName}))
    }

    handleChangeTargetLocation(coord, event) {
        this.setState(Object.assign(this.state.route,{targetLocation: coord}))
    }

    handleChangeTargetLocationGroupName(locationGroupName, event) {
        this.setState(Object.assign(this.state.route,{targetLocationGroupName: locationGroupName}))
    }

    handleChangeState(event) {
        console.log(event.target.value)
        this.setState(Object.assign(this.state.route,{enabled: (event.target.value == 'Enabled') ? false : true}))
    }

    render() {
        const { name, description, sourceLocationGroupName, sourceLocation, targetLocationGroupName, targetLocation, enabled } = this.state.route;
        return (
            <div className='row'>
                <div className="container">
                    <div className="row">
                        <h1>
                        <div className="text-left col-xs-1">
                            <button className="btn btn-default" onClick={this.handleCancel.bind(this)}><span className='glyphicon glyphicon-arrow-left'></span></button>
                        </div>
                        <div className="text-center col-xs-10">Create Route</div>
                        <div className="text-right col-xs-1">
                            <button className="btn btn-default" onClick={this.handleSave.bind(this)}><span className="glyphicon glyphicon-ok"></span></button>
                        </div>
                        </h1>
                    </div>
                </div>                
                <hr />
                <form className="form-horizontal">
                    <div className="container">
                        <div className="form-group">
                            <label htmlFor="name-txt" className="col-xs-2 col-form-label">Route ID</label>
                            <div className="col-xs-10">
                                <input className="form-control" type="text" id="name-txt" value={name} onChange={this.handleChangeName.bind(this)} />
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="description-txt" className="col-xs-2 col-form-label">Description</label>
                            <div className="col-xs-10">
                                <textarea className="form-control" id="description-txt" rows="3" value={description} onChange={this.handleChangeDescription.bind(this)} />
                            </div>
                        </div>
                        <div className="form-group">
                            <label htmlFor="sourceLocation-txt" className="col-xs-2 col-form-label">Source Location</label>
                            <Coordinate value={sourceLocation} onChange={this.handleChangeSourceLocation.bind(this)} elementsClassName="col-xs-2" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="sourceLocationGroup-txt" className="col-xs-2 col-form-label">Source Location Group</label>
                            <LocationGroupSelector value={sourceLocationGroupName} onChange={this.handleChangeSourceLocationGroupName.bind(this)} elementsClassName="col-xs-10" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="targetLocation-txt" className="col-xs-2 col-form-label">Target Location</label>
                            <Coordinate value={targetLocation} onChange={this.handleChangeTargetLocation.bind(this)} elementsClassName="col-xs-2" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="targetLocationGroup-txt" className="col-xs-2 col-form-label">Target Location Group</label>
                            <LocationGroupSelector value={targetLocationGroupName} onChange={this.handleChangeTargetLocationGroupName.bind(this)} elementsClassName="col-xs-10" />
                        </div>
                        <div className="form-group">
                            <label className="col-xs-2 col-form-label"></label>
                            <div className="col-xs-10">
                                <div className="form-check">
                                    <button className={enabled ? 'btn btn-success' : 'btn btn-danger'} type="button" onClick={this.handleChangeState.bind(this)} value={enabled ? 'Enabled' : 'Disabled'}>{enabled ? 'Enabled' : 'Disabled'}</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        );
    }
}

CreateForm.propTypes = {
  onBack: PropTypes.func.isRequired,
  onSave: PropTypes.func.isRequired,
}

export default CreateForm;