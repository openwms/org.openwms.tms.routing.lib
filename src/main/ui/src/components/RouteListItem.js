import React from 'react';
import PropTypes from 'prop-types';

import Button from './Button';

class RouteListItem extends React.Component {

    handleDelete(event) {
        this.props.onDelete(this.props.value.key, event)
    }

    handleModify(event) {
        this.props.onModify(this.props.value.name, event)
    }

    handleEnable() {
        this.props.onEnable(this.props.value.key, !this.props.value.enabled)
    }

    render() {
        return (
            <tr style={{color: this.props.value.enabled ? '' : '#ccc'}}>
                <td>{this.props.value.name}</td>
                <td>{this.props.value.description}</td>
                <td>{this.props.value.sourceLocation}</td>
                <td>{this.props.value.sourceLocationGroupName}</td>
                <td>{this.props.value.targetLocation}</td>
                <td>{this.props.value.targetLocationGroupName}</td>
                <td className="text-right">
                    <button type="button" className="btn btn-default" onClick={this.handleModify.bind(this)}><span className="glyphicon glyphicon-pencil"></span></button>
                    <button type="button" className="btn btn-default" onClick={this.handleDelete.bind(this)}><span className="glyphicon glyphicon-trash"></span></button>
                    <button type="button" className="btn btn-default" onClick={this.handleEnable.bind(this)}><span className={this.props.value.enabled ? 'glyphicon glyphicon-star' : 'glyphicon glyphicon-star-empty'}></span></button>
                </td>
            </tr>
        );
    }
}

RouteListItem.propTypes = {
    value: PropTypes.object,
    onModify: PropTypes.func.isRequired,
    onEnable: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
};

export default RouteListItem;