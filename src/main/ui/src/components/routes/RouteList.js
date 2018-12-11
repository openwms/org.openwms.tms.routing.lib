import React from 'react';
import PropTypes from 'prop-types';
import {withStyles} from '@material-ui/core/styles';
import Divider from '@material-ui/core/Divider';
import Typography from '@material-ui/core/Typography';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import IconButton from '@material-ui/core/IconButton';
import AddIcon from '@material-ui/icons/Add';
import RouteListItem from './RouteListItem';
import RouteDialog from "./RouteDialog";

const styles = theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing.unit * 3,
        overflowX: 'auto',
    },
    table: {
        minWidth: 700,
    },
    row: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.background.default,
        },
    },
});

const RouteList = (props) => {
    const { classes } = props;
    return (
            <Paper className={classes.root}>
                    <Table>
                        <TableBody>
                        <TableRow>
                            <TableCell>
                                <Typography variant="display1" gutterBottom>
                                    Routes
                                </Typography>
                                <Typography variant="body2" gutterBottom>
                                    A Route is used to determine the path a TransportUnit
                                    takes across the warehouse. The Route ia calculated on
                                    the fly and may change depending on the current
                                    Location and the current warehouse situation.
                                </Typography>

                            </TableCell>
                            <TableCell>
                                <Typography variant="body1" gutterBottom align="right">
                                    <IconButton onClick={props.onCreate}><AddIcon /></IconButton>
                                </Typography>
                            </TableCell>
                        </TableRow>
                        </TableBody>
                    </Table>
                    <Divider />
                    <Table>
                        <TableHead>
                            <TableRow>
                                <TableCell><Typography variant="subheading" gutterBottom>Route ID</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Description</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Source Location</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Source Location Group</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Target Location</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Target Location Group</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Actions</Typography></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {props.routes.map(route =>
                                <RouteListItem key={route.key} value={route} classes={props.classes} match={props.match}
                                    onModify={props.onModify}
                                    onChangeStatus={props.onChangeStatus}
                                    onDelete={props.onDelete} />
                                )
                            }
                        </TableBody>
                    </Table>
                    <RouteDialog
                        match={props.match}
                    />
            </Paper>
    )
};

RouteList.propTypes = {
    classes: PropTypes.object.isRequired,
    routes: PropTypes.array.isRequired,
    onCreate: PropTypes.func.isRequired,
    onModify: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
    onChangeStatus: PropTypes.func.isRequired,
};

export default withStyles(styles)(RouteList);