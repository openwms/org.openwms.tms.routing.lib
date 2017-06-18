import React from 'react';
import PropTypes from 'prop-types';

class LocationGroupSelector extends React.Component {

    constructor() {
        super();
        this.val = ''
    }

    handleChange(event) {
        console.log('value is '+event.target.value)
        this.val = event.target.value
        this.props.onChange(this.val)
    }

    render() {
        if (this.props.value) {
            this.val = this.props.value
        }
        return (
            <div className={this.props.elementsClassName}>
                <select value={this.val} onChange={this.handleChange.bind(this)} className="form-control" id={this.props.id} >
                    <option value=""></option>
                    <option value="ZILE">ZILE</option>
                    <option value="FGTIPP">FGTIPP</option>
                    <option value="FGPALETT">FGPALETT</option>
                    <option value="FGCARTON">FGCARTON</option>
                    <option value="FGAISLE1">FGAISLE1</option>
                </select>                        
            </div>
        );
    }
}

LocationGroupSelector.propTypes = {
    id: PropTypes.string,
    value: PropTypes.string,
    elementsClassName: PropTypes.string.isRequired,
    onChange: PropTypes.func.isRequired,
}

export default LocationGroupSelector;