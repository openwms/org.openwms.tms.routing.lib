import React from 'react';

import RouteListItem from './RouteListItem';

class RouteList extends React.Component {


    render() {

        const routes = [
            { name: "Route 1", description: "Route 1 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: true },
            { name: "Route 2", description: "Route 2 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: true },
            { name: "Route 3", description: "Route 3 Desc", sourceLocation: "sourceLocation", targetLocation: "targetLocation", sourceLocationGroupName: "sourceLocationGroupName", targetLocationGroupName: "targetLocationGroupName", enabled: true },
        ];

        return (
            <table className="table">
                <thead>
                    <tr>
                        <th>Route ID</th>
                        <th>Description</th>
                        <th>Source Location</th>
                        <th>Source Location Group</th>
                        <th>Target Location</th>
                        <th>Target Location Group</th>
                        <th>Enabled</th>
                    </tr>
                </thead>
                {routes.map(route => <RouteListItem value={route} />)}
            </table>
        );
    }
}

export default RouteList;