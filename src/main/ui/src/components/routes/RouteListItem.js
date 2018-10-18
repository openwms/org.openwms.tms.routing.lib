import React from 'react';
import PropTypes from 'prop-types';

const RouteListItem = (props) => {

    return (
        <tr style={{color: props.value.enabled ? '' : '#ccc'}}>
            <td>{props.value.name}</td>
            <td>{props.value.description}</td>
            <td>{props.value.sourceLocation}</td>
            <td>{props.value.sourceLocationGroupName}</td>
            <td>{props.value.targetLocation}</td>
            <td>{props.value.targetLocationGroupName}</td>
            <td className="text-right">
                <button type="button" className="btn btn-default" onClick={() => props.onModify(props.value)}><span className="glyphicon glyphicon-pencil"></span></button>
                <button type="button" className="btn btn-default" onClick={() => props.onDelete(props.value)}><span className="glyphicon glyphicon-trash"></span></button>
                <button type="button" className="btn btn-default" onClick={() => props.onEnable(props.value)}><span className={props.value.enabled ? 'glyphicon glyphicon-star' : 'glyphicon glyphicon-star-empty'}></span></button>
            </td>
        </tr>
    );
}

RouteListItem.propTypes = {
    value: PropTypes.object,
    onModify: PropTypes.func,
    onEnable: PropTypes.func,
    onDelete: PropTypes.func,
};

export default RouteListItem;