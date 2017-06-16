import React from 'react';
import PropTypes from 'prop-types';

import RouteListItem from './RouteListItem';

class RouteList extends React.Component {

    handleEnableRoute(event) {
        console.log(event.target.value)
    }

    handleDeleteRoute(route) {
        console.dir(route)
    }

    render() {
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
                            {this.props.routes.map(route => <RouteListItem key={route.name} value={route} onEnable={this.handleEnableRoute.bind(this)} onDelete={this.handleDeleteRoute.bind(this)} />)}
                        </tbody>
                    </table>
                </div>
            </div>
        );
    }
}

RouteList.propTypes = {
    routes: PropTypes.array.isRequired,
    onCreate: PropTypes.func.isRequired,
}

export default RouteList;