import React from 'react';
import PropTypes from 'prop-types';

import Header from './Header';
import RouteListItem from './RouteListItem';

class RouteList extends React.Component {

    handleEnableItem(event) {
        console.log(event.target.value)
    }

    render() {

        const routes = [
            { name: "Route 1", description: "Route 1 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: true },
            { name: "Route 2", description: "Route 2 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: true },
            { name: "Route 3", description: "Route 3 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: false },
        ];

        return (               
            <div>
                <div className='row'>
                    <div className="container">
                        <div className="row">
                            <h1>
                            <div className="col-md-10">Routes</div>
                            <div className="col-md-2"><button className="btn btn-default" onClick={this.props.onCreate}><span className="glyphicon glyphicon-plus"></span></button></div>
                            </h1>
                        </div>
                    </div>                
                </div>
                <hr />
                <div className="container">
                    <table className="table">
                        <thead>
                            <tr>
                                <th>Route ID</th>
                                <th>Description</th>
                                <th>Source Location</th>
                                <th>Source Location Group</th>
                                <th>Target Location</th>
                                <th>Target Location Group</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            {routes.map(route => <RouteListItem key={route.name} value={route} onEnable={this.handleEnableItem.bind(this)}/>)}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

RouteList.propTypes = {
  onCreate: PropTypes.func.isRequired,
}

export default RouteList;