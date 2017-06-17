import React from 'react';

import PropTypes from 'prop-types';

import Coordinate from './Coordinate';

class EditForm extends React.Component {

    constructor() {
        super()

        this.state = {
            obj: {
                name: '',
                description: '',
                sourceLocationGroup: '',
                targetLocationGroup: '',
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
//        const route = this.state.obj
//        route.name = event.target.value
        this.setState({ obj: Object.assign(this.state.obj, {name:event.target.value}) })
    }

    handleChangeDescription(event) {
        this.setState({ obj: Object.assign(this.state.obj, {description:event.target.value}) })
    }

    handleChangeState(event) {
        this.setState({ obj: Object.assign(this.state.obj, {enabled:!event.target.value}) })
    }

    handleChangeCoord(coord, event) {
ddd        this.setState({ obj: Object.assign(this.state.obj, {sourceLocation:coord}) })
    }

    render() {
        const { name, description, sourceLocationGroup, sourceLocation, targetLocationGroup, targetLocation, enabled } = this.state.obj;
        return (
            <div className='row'>
                <div className='row'>
                    <div className="container">
                        <div className="row">
                            <h1>
                            <div className="col-md-10">Create Route</div>
                            <div className="col-md-2">
                                <button className="btn btn-default" onClick={this.handleCancel.bind(this)}><span className="glyphicon glyphicon-trash"></span></button>
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
                            <Coordinate value={sourceLocation} onChange={this.handleChangeCoord.bind(this)}/>
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
  onBack: PropTypes.func.isRequired,
  onSave: PropTypes.func.isRequired,
}

export default EditForm;