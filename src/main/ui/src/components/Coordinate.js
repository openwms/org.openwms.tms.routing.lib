import React from 'react';
import PropTypes from 'prop-types';

class Coordinate extends React.Component {

    constructor() {
        super();
        this.state = {
            coord: this.props.value,
        }    
    }

    handleChange(event) {
        this.setState({ coord: event.target.value })
        this.props.onChange(this.state.coord, this.props.key)
    }

    render() {
        return (
            <input className="form-control" type="text" value={this.state.coord} onChange={this.handleChange.bind(this)} maxLength="4" />
        );
    }
}

Coordinate.propTypes = {
    key: PropTypes.string.isRequired,
    value: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
}

export default Coordinate;