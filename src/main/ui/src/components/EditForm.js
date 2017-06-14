import React from 'react';

class EditForm extends React.Component {
    render() {
        return (
            <form>
                <div className="container">
                    <div className="form-group row">
                        <label htmlFor="routeId-txt" className="col-4 col-form-label">Route ID</label>
                        <div className="col-8">
                            <input className="form-control" type="text" value="Artisanal kale" id="routeId-txt" />
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="description-txt" className="col-4 col-form-label">Description</label>
                        <div className="col-8">
                            <textarea className="form-control" value="Artisanal kale" id="description-txt" rows="3"/>
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="sourceLocation-txt" className="col-4 col-form-label">Source Location</label>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="sourceLocationArea-txt"/>
                        </div>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="sourceLocationAisle-txt"/>
                        </div>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="sourceLocationX-txt"/>
                        </div>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="sourceLocationX-txt"/>
                        </div>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="sourceLocationZ-txt"/>
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="sourceLocationGroup-txt" className="col-4 col-form-label">Source Location Group</label>
                        <div className="col-8">
                            <select className="form-control" id="sourceLocationGroup-txt">
                                <option>ZILE</option>
                                <option>FGTIPP</option>
                                <option>FGPALETT</option>
                                <option>FGCARTON</option>
                                <option>FGAISLE1</option>
                            </select>                        
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="targetLocation-txt" className="col-4 col-form-label">Target Location</label>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="targetLocationArea-txt"/>
                        </div>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="targetLocationAisle-txt"/>
                        </div>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="targetLocationX-txt"/>
                        </div>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="targetLocationX-txt"/>
                        </div>
                        <div className="col-1">
                            <input className="form-control" type="text" value="" id="targetLocationZ-txt"/>
                        </div>
                    </div>

                    <div className="form-group row">
                        <label htmlFor="targetLocationGroup-txt" className="col-4 col-form-label">Target Location Group</label>
                        <div className="col-8">
                            <select className="form-control" id="targetLocationGroup-txt">
                                <option>ZILE</option>
                                <option>FGTIPP</option>
                                <option>FGPALETT</option>
                                <option>FGCARTON</option>
                                <option>FGAISLE1</option>
                            </select>                        
                        </div>
                    </div>

                    <div className="form-group row">
                        <label className="col-4 col-form-label">Enabled</label>
                        <div className="col-8">
                            <div className="form-check">
                                <label className="form-check-label">
                                    <input className="form-check-input" type="checkbox"/>
                                </label>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        );
    }
}

export default EditForm;