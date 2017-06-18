import React from 'react';

import PropTypes from 'prop-types';

import Coordinate from './Coordinate';
import LocationGroupSelector from './LocationGroupSelector';

class EditForm extends React.Component {

    constructor() {
        super()
        this.state = {
            obj: {
                name: '',
                description: '',
                sourceLocationGroupName: '',
                targetLocationGroupName: '',
                enabled: true,
                sourceLocation: '',
                targetLocation: '',
            },
        }
    }

    handleSave(event) {
        this.props.onSave(this.state.obj);
    }

    handleCancel(event) {
        this.props.onBack();
    }

    handleChangeName(event) {
        this.setState({ obj: Object.assign(this.state.obj, {name:event.target.value}) })
    }

    handleChangeDescription(event) {
        this.setState({ obj: Object.assign(this.state.obj, {description:event.target.value}) })
    }

    handleChangeSourceLocation(coord, event) {
        this.setState({ obj: Object.assign(this.state.obj, {sourceLocation:coord}) })
    }

    handleChangeSourceLocationGroupName(locationGroupName, event) {
        this.setState({ obj: Object.assign(this.state.obj, {sourceLocationGroupName:locationGroupName}) })
    }

    handleChangeTargetLocation(coord, event) {
        this.setState({ obj: Object.assign(this.state.obj, {targetLocation:coord}) })
    }

    handleChangeTargetLocationGroupName(locationGroupName, event) {
        this.setState({ obj: Object.assign(this.state.obj, {targetLocationGroupName:locationGroupName}) })
    }

    handleChangeState(event) {
        this.setState({ obj: Object.assign(this.state.obj, {enabled:!this.state.obj.enabled}) })
    }

    render() {
        const { name, description, sourceLocationGroupName, sourceLocation, targetLocationGroupName, targetLocation, enabled } = this.props.value;
        return (
            <div className='row'>
                <div className='row'>
                    <div className="container">
                        <div className="row">
                            <h1>
                            <div className="col-md-10">{this.props.mode == 'create' ? 'Create Route' : 'Edit Route'}</div>
                            <div className="text-right col-md-2">
                                <button className="btn btn-default" onClick={this.handleCancel.bind(this)}><span className='glyphicon glyphicon-repeat'></span></button>
                                <button className="btn btn-default" onClick={this.handleSave.bind(this)}><span className="glyphicon glyphicon-ok"></span></button>
                            </div>
                            </h1>
                        </div>
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
                                    <button className={enabled ? 'btn btn-success' : 'btn btn-danger'} type="button" onClick={this.handleChangeState.bind(this)}>{enabled ? 'Enabled' : 'Disabled'}</button>
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
  mode: PropTypes.string.isRequired,
  onBack: PropTypes.func.isRequired,
  onSave: PropTypes.func.isRequired,
}

export default EditForm;