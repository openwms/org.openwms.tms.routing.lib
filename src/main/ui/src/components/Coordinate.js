import React from 'react';
import PropTypes from 'prop-types';

class Coordinate extends React.Component {

    constructor() {
        super();
        this.coord = {
            area:'',
            aisle:'',
            x:'',
            y:'',
            z:'',
        }
    }

    handleChange(event) {
        switch (event.target.alt) {
            case 'area':
                this.coord.area = event.target.value
                break;
            case 'aisle':
                this.coord.aisle = event.target.value
                break;
            case 'x':
                this.coord.x = event.target.value
                break;
            case 'y':
                this.coord.y = event.target.value
                break;
            case 'z':
                this.coord.z = event.target.value
                break;
            default:
                console.log('Error')
        }
        this.props.onChange("".concat(this.coord.area, '/', this.coord.aisle, '/', this.coord.x, '/', this.coord.y, '/', this.coord.z))
    }

    render() {
        if (this.props.value) {
            const coords = this.props.value.split('/')
            if (coords.length === 5) {
                this.coord = {
                    area: coords[0],
                    aisle: coords[1],
                    x: coords[2],
                    y: coords[3],
                    z: coords[4],
                }
            } else {
                throw Error('Not a valid coordinate passed as argument: ' + this.props.value)
            }
        }
        return (
            <div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.coord.area} onChange={this.handleChange.bind(this)} alt="area" maxLength="4" />
                </div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.coord.aisle} onChange={this.handleChange.bind(this)} alt="aisle" maxLength="4" />
                </div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.coord.x} onChange={this.handleChange.bind(this)} alt="x" maxLength="4" />
                </div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.coord.y} onChange={this.handleChange.bind(this)} alt="y" maxLength="4" />
                </div>
                <div className="col-xs-2">
                    <input className="form-control" type="text" value={this.coord.z} onChange={this.handleChange.bind(this)} alt="z" maxLength="4" />
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