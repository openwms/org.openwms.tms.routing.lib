import React from 'react';

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
                <td><input type="checkbox" class="custom-control-input" checked={this.props.value.enabled}/></td>
                <td><button type="button" class="btn btn-default" aria-label="Left Align"><span class="glyphicon glyphicon-align-left" aria-hidden="true"></span></button></td>
            </tr>
        );
    }
}

export default RouteListItem;