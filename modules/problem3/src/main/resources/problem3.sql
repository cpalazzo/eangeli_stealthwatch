/**
 * Assumptions:
 * 1. A many to many relationship between tables.
 * 2. For the first query, assume a seperate return for members for each location and organization.
 *    -This means a member may belong to the same organization multiple times in different locations and to different organizations.
 *    -The results are not grouped by location or organization as this was not explicitly requested.
 * 3. For the second query, the age is assumed to be greater than (but not equal to) 45.
 * 4. For the third query, the columns/information to be returned was not specified, so it simply returns the id and name of the member and the total dues owed
 *    even though the total dues owed will be zero.
 *    -The dues owed is a sum of all dues owed for the member for all organizations and locations.
 * 5. They may be a member but not in an organization.
 * 6. If a member is not in any organization(s), they will not be in the result set for the first query.
 * 7. If a member is not in any organization(s), they will be in the result set for the third query as they owe zero dues any where.
 * 8. Any member_id's in organization but not in members are ignored.
 */
select m.NAME, m.ADDRESS, o.DUES, o.LOCATION from members as m, organization as o where o.MEMBER_ID=m.ID;
select * from members where AGE>45;
select m.ID,m.NAME,d.dues from (select m.ID, sum(o.DUES) as dues from members as m, organization as o where o.MEMBER_ID=m.ID GROUP BY m.ID) as d, members as m where m.ID=d.ID and d.dues=0.00 or (m.ID not in (select MEMBER_ID from organization) and d.dues=0.00);