import React from 'react';
import PropTypes from 'prop-types';

class Coordinate extends React.Component {

    constructor() {
        super();
        this.state = {
                    coord: {
                    }
                }   
    }

    handleChange(event) {
        console.log('key: '+event.target.alt)
        console.log('val: '+event.target.value)
        switch (event.target.alt) {
            case 'area':
                this.setState({ coord: Object.assign(this.state.coord, {area:event.target.value}) })
                break;
            case 'aisle':
                this.setState({ coord: Object.assign(this.state.coord, {aisle:event.target.value}) })
                break;
            case 'x':
                this.setState({ coord: Object.assign(this.state.coord, {x:event.target.value}) })
                break;
            case 'y':
                this.setState({ coord: Object.assign(this.state.coord, {y:event.target.value}) })
                break;
            case 'z':
                this.setState({ coord: Object.assign(this.state.coord, {z:event.target.value}) })
                break;
            default:
                console.log('Error')
        }
        this.props.onChange("".concat(this.state.coord.area, '/', this.state.coord.aisle, '/', this.state.coord.x, '/', this.state.coord.y, '/', this.state.coord.z))
    }

    render() {
        console.log("value: " + this.props.value)
        if (this.props.value) {
            const coords = this.props.value.split('/')
            if (coords.length === 5) {
                this.setState(                    {coord: {
                        area: coords[0],
                        aisle: coords[1],
                        x: coords[2],
                        y: coords[3],
                        z: coords[4],
                    }}
)
            }
        }
        return (
            <div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.state.coord.area} onChange={this.handleChange.bind(this)} alt="area" maxLength="4" />
                </div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.state.coord.aisle} onChange={this.handleChange.bind(this)} alt="aisle" maxLength="4" />
                </div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.state.coord.x} onChange={this.handleChange.bind(this)} alt="x" maxLength="4" />
                </div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.state.coord.y} onChange={this.handleChange.bind(this)} alt="y" maxLength="4" />
                </div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.state.coord.z} onChange={this.handleChange.bind(this)} alt="z" maxLength="4" />
                </div>
            </div>
        );
    }
}

Coordinate.propTypes = {
    value: PropTypes.string,
    onChange: PropTypes.func.isRequired,
}

export default Coordinate;