import React from 'react';
import PropTypes from 'prop-types';
import { Table, Container, Row } from 'reactstrap';
import utils from '@ameba/ameba-js'
import RouteListItem from './RouteListItem';

const styles = theme => ({
    root: theme.mixins.gutters({
        paddingTop: 16,
        paddingBottom: 16,
        marginTop: theme.spacing.unit * 3,
    }),
    button: {
        marginLeft: theme.spacing.unit * 3,
    },
});

const RouteList = (props) => {

    const rows = utils.renderEmptyRows(
        5 - (props.routes ? props.routes.length : 0),
        (i) => <RouteListItem empty key={i} />
    );

    return (
            <Container>
                <Row>
                    <Container>
                        <Row>
                            <h1>
                                <div className="col-xs-11">Routes</div>
                                <div className="text-right col-xs-1"><button className="btn btn-default" onClick={props.onCreate}><span className="glyphicon glyphicon-plus"></span></button></div>
                            </h1>
                        </Row>
                    </Container>
                    <hr />
                    <Container>
                        <Row>
                            <Table>
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
                                    {props.routes.map(route =>
                                        <RouteListItem key={route.key} value={route}
                                            onModify={props.onModify}
                                            onEnable={props.onChangeStatus}
                                            onDelete={props.onDelete} />
                                        )
                                    }
                                </tbody>
                            </Table>
                        </Row>
                    </Container>
                </Row>
            </Container>
    )
}

RouteList.propTypes = {
    routes: PropTypes.array.isRequired,
    onCreate: PropTypes.func.isRequired,
    onModify: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
    onChangeStatus: PropTypes.func.isRequired,
}

export default RouteList;