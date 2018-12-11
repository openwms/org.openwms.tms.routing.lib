import React from 'react';
import PropTypes from 'prop-types';
import IconButton from '@material-ui/core/IconButton';
import TableCell from '@material-ui/core/TableCell';
import TableRow from '@material-ui/core/TableRow';
import EditIcon from '@material-ui/icons/Edit';
import DeleteIcon from '@material-ui/icons/Delete';
import StarIcon from '@material-ui/icons/Star';
import StarBorderIcon from '@material-ui/icons/StarBorder';
import Typography from "@material-ui/core/Typography/Typography";
import Tooltip from "@material-ui/core/Tooltip/Tooltip";

const ActionListItem = (props) => {
    const { classes } = props;
    return (
        <TableRow className={classes.row} style={{color: props.value.enabled ? '' : '#ccc'}}>
            <TableCell><Typography variant="subheading" gutterBottom>{props.value.name}</Typography></TableCell>
            <TableCell><Typography variant="subheading" gutterBottom>{props.value.type}</Typography></TableCell>
            <TableCell><Typography variant="subheading" gutterBottom>{props.value.description}</Typography></TableCell>
            <TableCell><Typography variant="subheading" gutterBottom>{props.value.route}</Typography></TableCell>
            <TableCell><Typography variant="subheading" gutterBottom>{props.value.program}</Typography></TableCell>
            <TableCell><Typography variant="subheading" gutterBottom>{props.value.location}</Typography></TableCell>
            <TableCell><Typography variant="subheading" gutterBottom>{props.value.locationGroupName}</Typography></TableCell>
            <TableCell className="text-right">
                <Tooltip
                    title="Edit"
                    placement={'bottom-start'}
                    enterDelay={300}>
                    <IconButton className="btn btn-default" onClick={() => props.onModify(props.value)}><EditIcon /></IconButton>
                </Tooltip>
                <Tooltip
                    title="Delete"
                    placement={'bottom-start'}
                    enterDelay={300}>
                    <IconButton className="btn btn-default" onClick={() => props.onDelete(props.value)}><DeleteIcon /></IconButton>
                </Tooltip>
                <Tooltip
                    title={props.value.enabled ? 'Deactivate' : 'Activate'}
                    placement={'bottom-start'}
                    enterDelay={300}>
                    <IconButton className="btn btn-default" onClick={() => {props.value.enabled = !props.value.enabled;props.onChangeStatus(props.value)}}>
                        {props.value.enabled ? <StarIcon /> : <StarBorderIcon />}</IconButton>
                </Tooltip>
            </TableCell>
        </TableRow>
    );
};

ActionListItem.propTypes = {
    classes: PropTypes.object.isRequired,
    value: PropTypes.object,
    onModify: PropTypes.func,
    onChangeStatus: PropTypes.func,
    onDelete: PropTypes.func,
};

export default ActionListItem;