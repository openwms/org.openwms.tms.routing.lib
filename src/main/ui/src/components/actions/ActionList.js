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
import ActionListItem from './ActionListItem';
import ActionDialog from "./ActionDialog";
import Snackbar from "@material-ui/core/Snackbar/Snackbar";

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

const ActionList = (props) => {
    const { classes } = props;
    return (
            <Paper className={classes.root}>
                    <Table>
                        <TableBody>
                            <TableRow>
                                <TableCell>
                                    <Typography variant="display1" gutterBottom>
                                        Actions
                                    </Typography>
                                    <Typography variant="body2" gutterBottom>
                                        Actions are join points and define what Program is executed if an
                                        event of the given Type occurs at a given Location or LocationGroup.
                                        This Action may then be executed by the workflow engine to control
                                        the material flow handling.
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
                                <TableCell><Typography variant="subheading" gutterBottom>Name</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Type</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Description</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Route</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Program</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Location</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Location Group</Typography></TableCell>
                                <TableCell><Typography variant="subheading" gutterBottom>Actions</Typography></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {props.actions.map(action =>
                                <ActionListItem key={action.key} value={action} classes={props.classes} match={props.match}
                                    onModify={props.onModify}
                                    onChangeStatus={props.onChangeStatus}
                                    onDelete={props.onDelete} />
                                )
                            }
                        </TableBody>
                    </Table>
                    <ActionDialog
                        match={props.match}
                    />
                    <Snackbar
                        anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
                        open={props.error != null}
                        onClose={props.onCloseToast}
                        autoHideDuration={5000}
                        ContentProps={{
                            'aria-describedby': 'message-id',
                        }}
                        message={<span id="message-id">{props.error ? props.error.message : ''}</span>}
                    />
            </Paper>
    )
};

ActionList.propTypes = {
    classes: PropTypes.object.isRequired,
    actions: PropTypes.array.isRequired,
    onCreate: PropTypes.func.isRequired,
    onModify: PropTypes.func.isRequired,
    onDelete: PropTypes.func.isRequired,
    onCloseToast: PropTypes.func.isRequired,
    onChangeStatus: PropTypes.func.isRequired,
};

export default withStyles(styles)(ActionList);