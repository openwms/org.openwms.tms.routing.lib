import React from 'react';
import PropTypes from 'prop-types';

import Button from './Button';

class RouteListItem extends React.Component {

    render() {
        return (
            <tr>
                <td>{this.props.value.name}</td>
                <td>{this.props.value.description}</td>
                <td>{this.props.value.sourceLocation}</td>
                <td>{this.props.value.sourceLocationGroupName}</td>
                <td>{this.props.value.targetLocation}</td>
                <td>{this.props.value.targetLocationGroupName}</td>
                <td><input type="checkbox" disabled checked={this.props.value.enabled} /></td>
                <td></td>
            </tr>
        );
    }
}

RouteListItem.propTypes = {
    value: PropTypes.object,
};

export default RouteListItem;