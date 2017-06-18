import React from 'react';
import PropTypes from 'prop-types';

import Button from './Button';

class RouteListItem extends React.Component {

    render() {
        return (
            <tr style={{color: this.props.value.enabled ? '' : '#ccc'}}>
                <td>{this.props.value.name}</td>
                <td>{this.props.value.description}</td>
                <td>{this.props.value.sourceLocation}</td>
                <td>{this.props.value.sourceLocationGroupName}</td>
                <td>{this.props.value.targetLocation}</td>
                <td>{this.props.value.targetLocationGroupName}</td>
                <td>
                    <button type="button" className="btn btn-default"><span className="glyphicon glyphicon-pencil"></span></button>
                    <button type="button" className="btn btn-default" onClick={this.props.onDelete}><span className="glyphicon glyphicon-remove"></span></button>
                    <button type="button" className="btn btn-default"><span className={this.props.value.enabled ? 'glyphicon glyphicon-star' : 'glyphicon glyphicon-star-empty'}></span></button>
                </td>
            </tr>
        );
    }
}

RouteListItem.propTypes = {
    value: PropTypes.object,
    onDelete: PropTypes.func.isRequired,
};

export default RouteListItem;