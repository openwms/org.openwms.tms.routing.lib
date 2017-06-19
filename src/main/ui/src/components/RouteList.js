import React from 'react';
import PropTypes from 'prop-types';

import RouteListItem from './RouteListItem';

class RouteList extends React.Component {

    handleModifyRoute(routeName, event) {
        this.props.onModify(routeName, event)
    }

    handleEnableRoute(event) {
        console.log(event.target.value)
    }

    handleDeleteRoute(routeName, event) {
        this.props.onDelete(routeName)
    }

    render() {
        return (
            <div className='row'>
                <div className="container">
                    <div className="row">
                        <h1>
                            <div className="col-xs-11">Routes</div>
                            <div className="text-right col-xs-1"><button className="btn btn-default" onClick={this.props.onCreate}><span className="glyphicon glyphicon-plus"></span></button></div>
                        </h1>
                    </div>
                </div>                
                <hr />
                <div className="container">
                    <div className="row">
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
                                {this.props.routes.map(route =>
                                    <RouteListItem key={route.name} value={route}
                                        onModify={this.handleModifyRoute.bind(this)}
                                        onEnable={this.handleEnableRoute.bind(this)}
                                        onDelete={this.handleDeleteRoute.bind(this)} />
                                    )
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        );
    }
}

RouteList.propTypes = {
    routes: PropTypes.array.isRequired,
    onCreate: PropTypes.func.isRequired,
    onModify: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
}

export default RouteList;